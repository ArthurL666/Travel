<template>
  <div class="page-container">
    <div class="page-header" style="display:flex;align-items:center;justify-content:space-between;">
      <div>
        <h2>📋 我的行程</h2>
        <p>已保存的旅行计划，点击可查看详情</p>
      </div>
      <button v-if="plans.length" class="btn btn-outline btn-sm" @click="refresh">🔄 刷新</button>
    </div>

    <!-- 加载中 -->
    <div v-if="loading" class="empty-state"><p>⏳ 加载中...</p></div>

    <!-- 空状态 -->
    <div v-else-if="!plans.length" class="empty-state">
      <span class="icon">🗺️</span>
      <h3>暂无旅行计划</h3>
      <p>去 AI 规划中聊聊，保存行程后就会出现在这里 ✨</p>
    </div>

    <!-- 行程卡片列表 -->
    <div v-else class="plans-enhanced">
      <div
        v-for="plan in plans"
        :key="plan.id"
        class="plan-card-enhanced"
        :class="{ expanded: expandedId === plan.id }"
      >
        <!-- 卡片头部 -->
        <div class="plan-card-header" @click="toggleExpand(plan.id)">
          <div class="plan-card-left">
            <span class="plan-card-icon">📍</span>
            <div>
              <h3 class="plan-card-city">{{ plan.city || '未知目的地' }}</h3>
              <div class="plan-card-meta">
                <span v-if="plan.days" class="plan-card-days">📅 {{ plan.days }} 天</span>
                <span class="plan-card-date">{{ fmtDate(plan.createTime) }}</span>
              </div>
            </div>
          </div>
          <div class="plan-card-actions">
            <button class="btn btn-text btn-sm" @click.stop="del(plan.id)" title="删除">
              🗑️
            </button>
            <span class="expand-icon">{{ expandedId === plan.id ? '▲' : '▼' }}</span>
          </div>
        </div>

        <!-- 折叠内容 -->
        <div v-if="expandedId === plan.id" class="plan-card-body">
          <div class="plan-card-content" v-html="renderContent(plan.content)"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'
import { useToast } from '../composables/useToast'

const plans = ref([])
const loading = ref(true)
const expandedId = ref(null)
const toast = useToast()

onMounted(() => load())

async function load() {
  loading.value = true
  try {
    const res = await api.get('/api/chat/plan/list')
    if (res.data.code === 200) plans.value = res.data.data || []
  } catch (e) { toast.error('加载失败') }
  loading.value = false
}

async function refresh() {
  await load()
  toast.success('已刷新')
}

function toggleExpand(id) {
  expandedId.value = expandedId.value === id ? null : id
}

async function del(id) {
  if (!confirm('确定删除这个行程吗？')) return
  try {
    await api.delete(`/api/chat/plan/${id}`)
    plans.value = plans.value.filter(p => p.id !== id)
    if (expandedId.value === id) expandedId.value = null
    toast.success('删除成功')
  } catch (e) { toast.error('删除失败') }
}

function renderContent(text) {
  if (!text) return '<p style="color:var(--text-light)">暂无详情</p>'
  // 将换行转为 <br>，将 **加粗** 转为 <strong>，清理残余 **
  return text
    .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
    .replace(/\n/g, '<br>')
    .replace(/\*\*/g, '')
}

function fmtDate(ts) {
  if (!ts) return ''
  return new Date(ts).toLocaleString('zh-CN', {
    month: 'short', day: 'numeric',
    hour: '2-digit', minute: '2-digit'
  })
}
</script>

<style scoped>
/* ===== 行程卡片 ===== */
.plans-enhanced {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.plan-card-enhanced {
  background: #fff;
  border-radius: 16px;
  border: 1px solid var(--border);
  overflow: hidden;
  transition: box-shadow 0.2s;
}

.plan-card-enhanced:hover {
  box-shadow: var(--shadow-sm);
}

/* 卡片头部 */
.plan-card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  cursor: pointer;
  transition: background 0.15s;
}

.plan-card-header:hover {
  background: var(--bg);
}

.plan-card-left {
  display: flex;
  align-items: center;
  gap: 14px;
}

.plan-card-icon {
  font-size: 28px;
  flex-shrink: 0;
}

.plan-card-city {
  font-size: 17px;
  font-weight: 700;
  color: var(--text);
  margin: 0 0 2px;
}

.plan-card-meta {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 12px;
  color: var(--text-light);
}

.plan-card-days {
  background: var(--bg);
  padding: 1px 8px;
  border-radius: 8px;
  color: var(--primary);
  font-weight: 600;
}

.plan-card-date {
  color: var(--text-light);
}

.plan-card-actions {
  display: flex;
  align-items: center;
  gap: 4px;
}

.expand-icon {
  font-size: 10px;
  color: var(--text-light);
  width: 20px;
  text-align: center;
}

/* 卡片展开内容 */
.plan-card-body {
  border-top: 1px solid var(--border);
  padding: 16px 20px;
  animation: expandIn 0.2s ease-out;
}

@keyframes expandIn {
  from { opacity: 0; max-height: 0; padding-top: 0; padding-bottom: 0; }
  to { opacity: 1; max-height: 2000px; padding-top: 16px; padding-bottom: 16px; }
}

.plan-card-content {
  font-size: 14px;
  line-height: 1.8;
  color: var(--text);
}

.plan-card-content :deep(br) {
  content: '';
  display: block;
  margin: 4px 0;
}
</style>
