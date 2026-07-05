<template>
  <div class="chat-page">
    <div class="chat-main">
      <div class="chat-header">
        <h2>💬 AI 对话</h2>
        <span class="ai-badge">🟢 在线</span>
      </div>

      <div class="chat-messages" ref="msgContainer">
        <div v-for="(msg, idx) in messages" :key="idx" class="message-row" :class="msg.role">
          <div class="msg-avatar" :class="msg.role === 'assistant' ? 'ai' : 'usr'">
            {{ msg.role === 'assistant' ? '🤖' : '😊' }}
          </div>
          <div class="message-bubble">
            <div v-if="msg.role === 'assistant'" v-html="renderMd(msg.content)"></div>
            <div v-else>{{ msg.content }}</div>
            <div v-if="typing && idx === messages.length-1 && !msg.content" class="typing-cursor"></div>
            <div class="msg-time">{{ fmtTime(msg.timestamp) }}</div>
            <button v-if="msg.role === 'assistant' && msg.content && !(typing && idx === messages.length-1)"
              class="btn-save-plan" @click="savePlan(msg)">💾 保存行程</button>
          </div>
        </div>
      </div>

      <div class="chat-input-area">
        <div class="chat-hints">
          <span v-for="h in hints" :key="h" class="chat-hint" @click="sendHint(h)">{{ h }}</span>
        </div>
        <div class="chat-input-row">
          <textarea
            v-model="input"
            placeholder="告诉我想去哪里，我来帮你规划..."
            @keydown="onKeydown"
            :disabled="typing"
            rows="1"
          ></textarea>
          <button class="btn-send" @click="send()" :disabled="!input.trim() || typing">✈️</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useChat } from '../composables/useChat'
import api from '../api'
import { useToast } from '../composables/useToast'

const msgContainer = ref(null)
const {
  messages, input, typing, hints,
  init, send, sendHint, onKeydown,
  renderMd, fmtTime, scrollTo
} = useChat()

const toast = useToast()

async function savePlan(msg) {
  // 尝试从消息内容中提取城市和天数
  let city = ''
  let days = null
  const cityMatch = msg.content.match(/[一-龥]{2,4}(?=\s*\d{1,2}天)/)
  const daysMatch = msg.content.match(/(\d+)\s*天/)
  if (cityMatch) city = cityMatch[0]
  if (daysMatch) days = parseInt(daysMatch[1])
  // 兜底：让用户输入
  if (!city) city = prompt('请输入目的地城市：')
  if (!days) days = parseInt(prompt('请输入旅行天数：') || '0')
  if (!city || !days) { toast.error('保存失败：城市或天数不能为空'); return }
  try {
    await api.post('/api/chat/plan/save', { city, days, content: msg.content, preferences: '' })
    toast.success('✅ 行程已保存到「我的行程」')
  } catch (e) {
    toast.error('保存失败，请重试')
  }
}

onMounted(() => {
  init()
  scrollTo(msgContainer.value)
})

// 消息更新时自动滚动
watch(messages, () => scrollTo(msgContainer.value), { deep: true })
</script>
