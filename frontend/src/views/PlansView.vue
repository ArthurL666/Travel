<template>
  <div class="page-container">
    <div class="page-header">
      <div class="header-left">
        <h2>📋 我的行程</h2>
        <p v-if="plans.length">共 {{ plans.length }} 个旅行计划</p>
        <p v-else>已保存的旅行计划，可随时查看</p>
      </div>
      <button v-if="plans.length" class="btn btn-outline btn-sm" @click="refresh">🔄 刷新</button>
    </div>

    <!-- ========== 加载中 ========== -->
    <div v-if="loading" class="loading-state">
      <div class="loading-spinner"></div>
      <p class="loading-text">加载中...</p>
    </div>

    <!-- ========== 空状态 ========== -->
    <div v-else-if="!plans.length" class="empty-state">
      <span class="empty-icon">🗺️</span>
      <h3>暂无旅行计划</h3>
      <p>去 AI 规划中聊聊目的地，保存行程后就会出现在这里 ✨</p>
      <button class="btn btn-primary" style="width:auto;margin-top:20px;padding:10px 28px;border-radius:12px" @click="goChat">
        开始规划 →
      </button>
    </div>

    <!-- ========== 行程列表 ========== -->
    <div v-else class="plans-list">
      <TransitionGroup name="card">
        <div
          v-for="plan in plans"
          :key="plan.id"
          class="plan-card"
          :class="{ expanded: expandedId === plan.id }"
        >
          <!-- ===== 封面 ===== -->
          <div class="plan-cover" @click="toggleExpand(plan.id)">
            <div class="plan-cover-bg" :style="coverStyle(plan.city)"></div>
            <div class="plan-cover-overlay"></div>
            <div class="plan-cover-content">
              <div class="plan-cover-top">
                <div class="plan-cover-left">
                  <span class="plan-cover-icon">{{ cityIcon(plan.city) }}</span>
                  <div>
                    <h3 class="plan-city-name">{{ plan.city || '未知目的地' }}</h3>
                    <div class="plan-meta-tags">
                      <span class="plan-badge days-badge">📅 {{ plan.days }} 天{{ plan.days > 1 ? ' ' + (plan.days - 1) + ' 晚' : '' }}</span>
                      <span class="plan-badge date-badge">{{ fmtDate(plan.createTime) }}</span>
                    </div>
                  </div>
                </div>
                <div class="plan-cover-right">
                  <button class="cover-btn delete-btn" @click.stop="del(plan.id)" title="删除">
                    🗑️
                  </button>
                  <span class="expand-arrow" :class="{ open: expandedId === plan.id }">▼</span>
                </div>
              </div>

              <!-- 折叠预览 -->
              <Transition name="preview-fade">
                <div v-if="expandedId !== plan.id" class="plan-preview" key="preview">
                  <div class="preview-chips" v-if="dayCount(plan.content) > 0">
                    <span
                      v-for="i in Math.min(dayCount(plan.content), 4)"
                      :key="i"
                      class="preview-chip"
                    >Day {{ i }}</span>
                    <span v-if="dayCount(plan.content) > 4" class="preview-chip more">
                      +{{ dayCount(plan.content) - 4 }}
                    </span>
                  </div>
                  <p class="preview-text">{{ previewText(plan.content) }}</p>
                </div>
              </Transition>
            </div>
          </div>

          <!-- ===== 展开详情（动态交互） ===== -->
          <Transition name="detail-slide">
            <div v-if="expandedId === plan.id" class="plan-body" key="body">
              <!-- Day 选项卡 -->
              <div class="day-tabs-bar">
                <button
                  v-for="(day, idx) in parsedDays(plan.content)"
                  :key="day.dayNum"
                  class="day-tab"
                  :class="{ active: activeDay === idx }"
                  @click="switchDay(plan.id, idx)"
                >
                  <span class="day-tab-num">{{ day.dayNum }}</span>
                  <span class="day-tab-label">Day {{ day.dayNum }}</span>
                </button>
              </div>

              <!-- Day 内容卡片（滑动切换） -->
              <div class="day-content-area">
                <Transition :name="slideDir" mode="out-in">
                  <div class="day-panel" :key="activeDay" v-if="currentDay">
                    <div class="day-panel-header">
                      <h4 class="day-panel-title">{{ currentDay.title }}</h4>
                      <span class="day-panel-meta">{{ currentDay.items.length }} 项活动</span>
                    </div>

                    <TransitionGroup name="activity" tag="div" class="day-items">
                      <div
                        v-for="(item, j) in currentDay.items"
                        :key="dayNumKey + '-' + j"
                        class="day-item"
                      >
                        <span class="item-text">{{ item.text }}</span>
                      </div>
                      <div v-if="!currentDay.items.length" key="empty" class="day-item empty">
                        <span class="item-text">自由活动</span>
                      </div>
                    </TransitionGroup>
                  </div>
                </Transition>
              </div>

              <!-- 原始内容 -->
              <details class="raw-toggle">
                <summary class="raw-summary">📄 查看原始内容</summary>
                <div class="raw-body" v-html="renderContent(plan.content)"></div>
              </details>
            </div>
          </Transition>
        </div>
      </TransitionGroup>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import api from '../api'
import { useToast } from '../composables/useToast'

const router = useRouter()
const toast = useToast()

const plans = ref([])
const loading = ref(true)
const expandedId = ref(null)
const activeDay = ref(0)
const slideDir = ref('next')

// ===== 渐变色 =====
const GRADIENTS = [
  'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
  'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
  'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
  'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)',
  'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
  'linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)',
  'linear-gradient(135deg, #fccb90 0%, #d57eeb 100%)',
  'linear-gradient(135deg, #fdbb2d 0%, #22c1c3 100%)',
  'linear-gradient(135deg, #f5576c 0%, #ff6f00 100%)',
  'linear-gradient(135deg, #0EA5E9 0%, #38BDF8 100%)',
  'linear-gradient(135deg, #11998e 0%, #38ef7d 100%)',
  'linear-gradient(135deg, #fc5c7d 0%, #6a82fb 100%)',
]

function hashCode(s) {
  if (!s) return 0
  let h = 0
  for (let i = 0; i < s.length; i++) { h = ((h << 5) - h) + s.charCodeAt(i); h |= 0 }
  return Math.abs(h)
}

function coverStyle(city) {
  return { background: GRADIENTS[hashCode(city) % GRADIENTS.length] }
}

// ===== 城市图标 =====
const CITY_ICONS = {
  '北京': '🏯', '上海': '🌃', '广州': '🌺', '深圳': '🌳',
  '成都': '🐼', '杭州': '🏞️', '西安': '🏛️', '重庆': '🌉',
  '厦门': '🌊', '昆明': '🌸', '三亚': '🏖️', '大理': '🏔️',
  '丽江': '🏘️', '长沙': '🍊', '武汉': '🌸', '南京': '🏛️',
  '青岛': '🌊', '大连': '⛵', '苏州': '🏯', '桂林': '🏞️',
  '西藏': '⛰️', '新疆': '🏜️', '香港': '🌃', '澳门': '🎰',
  '台北': '🏯', '东京': '🗼', '首尔': '🇰🇷', '曼谷': '🙏',
  '巴黎': '🗼', '纽约': '🗽', '伦敦': '🇬🇧', '悉尼': '🏄',
  '罗马': '🏛️', '巴厘岛': '🌴', '普吉岛': '🏖️', '济州岛': '🌊',
}

function cityIcon(city) { return CITY_ICONS[city] || '📍' }

// ===== 内容解析 =====
function parsedDays(content) {
  if (!content) return []
  const days = []
  const lines = content.split('\n')
  let cur = null

  for (const raw of lines) {
    const line = raw.trim()
    if (!line) continue
    const text = line.replace(/^\*{1,2}\s*/, '').replace(/\s*\*{1,2}$/, '')
    const m = text.match(/^(?:Day\s*(\d+)|第\s*(\d+)\s*天)\s*[:：]?\s*(.*)/i)
    if (m) {
      if (cur) days.push(cur)
      const n = parseInt(m[1] || m[2])
      cur = { dayNum: n, title: m[3]?.trim() || `第 ${n} 天`, items: [] }
      continue
    }
    if (cur) {
      const im = line.match(/^[-*•]\s*(.*)$/) || line.match(/^\d+[.、]\s*(.*)$/)
      if (im) { cur.items.push({ text: im[1] }) }
      else if (!line.startsWith('**') && !line.startsWith('第') && line.length > 2) { cur.items.push({ text: line }) }
    }
  }
  if (cur) days.push(cur)
  if (!days.length && lines.some(l => l.trim())) {
    days.push({
      dayNum: 1,
      title: '行程安排',
      items: lines.filter(l => l.trim()).map(l => ({ text: l.trim().replace(/^[-*•]\s*/, '') }))
    })
  }
  return days
}

function dayCount(content) { return parsedDays(content).length }

const currentDay = computed(() => {
  if (!expandedId.value) return null
  const plan = plans.value.find(p => p.id === expandedId.value)
  if (!plan) return null
  const days = parsedDays(plan.content)
  return days[activeDay.value] || null
})

// activeDay 跨卡片独立存储：每个 planId 对应一个索引
const dayMap = ref({})

function switchDay(planId, idx) {
  const prev = dayMap.value[planId] ?? 0
  slideDir.value = idx > prev ? 'next' : 'prev'
  dayMap.value[planId] = idx
  activeDay.value = idx
}

function dayNumKey() { return expandedId.value + '-' + activeDay.value }

// ===== 活动图标 =====
function activityIcon(text) {
  const t = text || ''
  if (/吃|餐|美食|饭|海鲜|火锅|烧烤|早餐|午餐|晚餐|餐厅|小吃|品尝|料理|自助/i.test(t)) return '🍽️'
  if (/酒店|住|宿|入住|住宿|民宿|旅馆|客栈|房间/i.test(t)) return '🏨'
  if (/景点|游览|参观|看|博物馆|公园|寺庙|宫殿|遗址|名胜|古迹|景区/i.test(t)) return '🏛️'
  if (/交通|车|飞|机场|站|地铁|出租|公交|租车|高铁|火车|航班|大巴/i.test(t)) return '🚗'
  if (/购物|买|逛街|商场|市场|商业街|免税/i.test(t)) return '🛍️'
  if (/海滩|海|水|游泳|潜水|冲浪|海边|沙滩|浮潜|深潜|海景/i.test(t)) return '🏖️'
  if (/山|爬山|登山|徒步|自然|森林|峡谷/i.test(t)) return '⛰️'
  if (/拍照|照片|摄影|拍|相机|打卡/i.test(t)) return '📸'
  if (/晚上|夜|夜景|酒吧|夜市|夜生活|晚餐/i.test(t)) return '🌙'
  if (/上午|早上|早晨|清晨|日出/i.test(t)) return '🌅'
  if (/表演|演出|歌舞|show|秀|剧场/i.test(t)) return '🎭'
  if (/寺庙|佛教|祈福|许愿|宗教/i.test(t)) return '🙏'
  if (/咖啡|茶馆|下午茶|饮品/i.test(t)) return '☕'
  if (/骑行|单车|自行车|骑行/i.test(t)) return '🚴'
  if (/乐园|游乐|主题公园|迪士尼|欢乐谷/i.test(t)) return '🎢'
  if (/按摩|spa|温泉|泡汤|放松|SPA/i.test(t)) return '💆'
  if (/下午/i.test(t)) return '☀️'
  return '✨'
}

function previewText(content) {
  if (!content) return ''
  return content
    .replace(/\*\*(.+?)\*\*/g, '$1').replace(/[-*•]\s*/g, '')
    .replace(/#{1,6}\s*/g, '').replace(/\n/g, ' ').replace(/\s+/g, ' ').trim()
    .substring(0, 72).replace(/\s+\S*$/, '') + '...'
}

function renderContent(text) {
  if (!text) return '<p style="color:var(--text-light)">暂无详情</p>'
  return text.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>').replace(/\n/g, '<br>').replace(/\*\*/g, '')
}

function fmtDate(ts) {
  if (!ts) return ''
  return new Date(ts).toLocaleString('zh-CN', { month: 'short', day: 'numeric', hour: '2-digit', minute: '2-digit' })
}

// ===== 操作 =====
onMounted(() => load())

async function load() {
  loading.value = true
  try {
    const res = await api.get('/api/chat/plan/list')
    if (res.data.code === 200) plans.value = res.data.data || []
  } catch { toast.error('加载失败') }
  loading.value = false
}

async function refresh() {
  await load()
  toast.success('已刷新')
}

function toggleExpand(id) {
  if (expandedId.value === id) {
    expandedId.value = null
    activeDay.value = 0
  } else {
    expandedId.value = id
    activeDay.value = dayMap.value[id] ?? 0
  }
}

async function del(id) {
  if (!confirm('确定删除这个行程吗？')) return
  try {
    await api.delete(`/api/chat/plan/${id}`)
    plans.value = plans.value.filter(p => p.id !== id)
    if (expandedId.value === id) { expandedId.value = null; activeDay.value = 0 }
    toast.success('删除成功')
  } catch { toast.error('删除失败') }
}

function goChat() { router.push('/app/chat') }
</script>

<style scoped>
/* ===== 加载状态 ===== */
.loading-state {
  display: flex; flex-direction: column; align-items: center;
  justify-content: center; padding: 80px 20px; gap: 16px;
}
.loading-spinner {
  width: 40px; height: 40px;
  border: 3px solid var(--border);
  border-top-color: var(--primary);
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }
.loading-text { color: var(--text-light); font-size: 14px; }

/* ===== 行程列表 ===== */
.plans-list { display: flex; flex-direction: column; gap: 20px; }

/* ===== 卡片删除动画 ===== */
.card-enter-active, .card-leave-active {
  transition: all 0.4s ease;
}
.card-enter-from { opacity: 0; transform: translateY(20px); }
.card-leave-to { opacity: 0; transform: scale(0.9) translateY(-10px); }
.card-move { transition: transform 0.4s ease; }

/* ===== 卡片 ===== */
.plan-card {
  background: #fff;
  border-radius: var(--radius-lg);
  overflow: hidden;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border);
  transition: box-shadow 0.3s, transform 0.2s;
}
.plan-card:hover { box-shadow: var(--shadow-md); }
.plan-card.expanded { box-shadow: var(--shadow-md); }

/* ===== 封面 ===== */
.plan-cover {
  position: relative; cursor: pointer; overflow: hidden;
  min-height: 88px;
  -webkit-user-select: none; user-select: none;
}
.plan-cover-bg {
  position: absolute; inset: 0;
  transition: transform 0.5s ease;
}
.plan-card:hover .plan-cover-bg { transform: scale(1.04); }
.plan-cover-overlay {
  position: absolute; inset: 0;
  background: linear-gradient(135deg, rgba(0,0,0,0.4) 0%, rgba(0,0,0,0.15) 60%, rgba(0,0,0,0.05) 100%);
}
.plan-cover-content {
  position: relative; z-index: 1; padding: 18px 24px; color: #fff;
}
.plan-cover-top { display: flex; align-items: flex-start; justify-content: space-between; }
.plan-cover-left { display: flex; align-items: center; gap: 14px; }
.plan-cover-icon {
  font-size: 34px; line-height: 1; flex-shrink: 0;
  filter: drop-shadow(0 2px 6px rgba(0,0,0,0.25));
}
.plan-city-name {
  font-size: 20px; font-weight: 700; margin: 0 0 3px;
  text-shadow: 0 1px 4px rgba(0,0,0,0.2);
}
.plan-meta-tags { display: flex; align-items: center; gap: 6px; flex-wrap: wrap; }
.plan-badge {
  display: inline-block; padding: 2px 10px; border-radius: 20px;
  font-size: 11px; font-weight: 600;
  backdrop-filter: blur(8px);
  background: rgba(255,255,255,0.2);
  border: 1px solid rgba(255,255,255,0.25);
}
.plan-cover-right { display: flex; align-items: center; gap: 4px; flex-shrink: 0; }

.cover-btn {
  background: rgba(255,255,255,0.2);
  border: 1px solid rgba(255,255,255,0.3);
  backdrop-filter: blur(8px); color: #fff;
  width: 32px; height: 32px; border-radius: 50%;
  display: flex; align-items: center; justify-content: center;
  cursor: pointer; font-size: 14px;
  transition: all 0.25s; opacity: 0.7;
}
.plan-card:hover .cover-btn { opacity: 1; }
.cover-btn:hover { background: rgba(239,68,68,0.75); border-color: transparent; transform: scale(1.12); }

.expand-arrow {
  font-size: 11px; opacity: 0.75; padding: 6px 4px;
  transition: transform 0.3s ease;
}
.expand-arrow.open { transform: rotate(180deg); }

/* ===== 折叠预览 ===== */
.plan-preview { margin-top: 10px; padding-top: 10px; border-top: 1px solid rgba(255,255,255,0.2); }
.preview-chips { display: flex; align-items: center; gap: 6px; flex-wrap: wrap; margin-bottom: 6px; }
.preview-chip {
  display: inline-block; padding: 2px 10px; border-radius: 12px;
  font-size: 11px; font-weight: 600;
  background: rgba(255,255,255,0.22); border: 1px solid rgba(255,255,255,0.28);
}
.preview-chip.more { background: rgba(255,255,255,0.12); }
.preview-text {
  font-size: 13px; opacity: 0.8; line-height: 1.5;
  overflow: hidden; text-overflow: ellipsis;
  display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;
}

.preview-fade-enter-active, .preview-fade-leave-active { transition: opacity 0.25s ease; }
.preview-fade-enter-from, .preview-fade-leave-to { opacity: 0; }

/* ===== 展开内容 ===== */
.plan-body { border-top: 1px solid var(--border); background: #fff; }

.detail-slide-enter-active, .detail-slide-leave-active {
  transition: all 0.3s ease;
}
.detail-slide-enter-from { opacity: 0; max-height: 0; }
.detail-slide-enter-to { opacity: 1; max-height: 2000px; }
.detail-slide-leave-to { opacity: 0; max-height: 0; padding: 0; }

/* ===== Day 选项卡栏 ===== */
.day-tabs-bar {
  display: flex; gap: 6px; padding: 16px 24px 0;
  overflow-x: auto; scrollbar-width: none; -ms-overflow-style: none;
}
.day-tabs-bar::-webkit-scrollbar { display: none; }

.day-tab {
  flex-shrink: 0;
  display: flex; align-items: center; gap: 6px;
  padding: 7px 16px 7px 12px;
  border-radius: 20px;
  border: 1.5px solid var(--border);
  background: #fff;
  cursor: pointer;
  font-size: 13px; font-weight: 500; color: var(--text-secondary);
  transition: all 0.25s ease;
  font-family: inherit;
  position: relative;
}
.day-tab:hover {
  border-color: var(--primary-light);
  background: var(--bg);
  color: var(--primary-dark);
}
.day-tab.active {
  border-color: var(--primary);
  background: linear-gradient(135deg, #E0F2FE, #BAE6FD);
  color: var(--primary-dark);
  font-weight: 600;
}
.day-tab-num {
  display: inline-flex; align-items: center; justify-content: center;
  width: 22px; height: 22px; border-radius: 50%;
  background: var(--border);
  color: var(--text-secondary);
  font-size: 11px; font-weight: 700;
  transition: all 0.25s;
}
.day-tab.active .day-tab-num {
  background: var(--primary);
  color: #fff;
}
.day-tab-label { white-space: nowrap; }

/* ===== Day 内容区 ===== */
.day-content-area {
  padding: 16px 24px;
  min-height: 100px;
  overflow: hidden;
}

/* 滑动切换动画 */
.next-enter-active, .next-leave-active,
.prev-enter-active, .prev-leave-active {
  transition: all 0.3s ease;
}
.next-enter-from { opacity: 0; transform: translateX(30px); }
.next-leave-to { opacity: 0; transform: translateX(-20px); }
.prev-enter-from { opacity: 0; transform: translateX(-30px); }
.prev-leave-to { opacity: 0; transform: translateX(20px); }

.day-panel-header {
  display: flex; align-items: baseline; justify-content: space-between; gap: 12px;
  margin-bottom: 14px;
}
.day-panel-title {
  font-size: 16px; font-weight: 700; color: var(--text); margin: 0;
}
.day-panel-meta {
  font-size: 12px; color: var(--text-light); white-space: nowrap;
}

/* ===== 活动列表 ===== */
.day-items { display: flex; flex-direction: column; gap: 8px; }

.day-item {
  display: flex; align-items: flex-start; gap: 10px;
  padding: 10px 14px;
  background: var(--bg);
  border: 1px solid transparent;
  border-radius: var(--radius-sm);
  transition: all 0.2s ease;
  cursor: default;
}
.day-item:hover {
  background: #fff;
  border-color: var(--border);
  box-shadow: var(--shadow-sm);
  transform: translateX(4px);
}

.day-item.empty {
  background: transparent; border-color: transparent;
  font-style: italic; color: var(--text-light);
  cursor: default;
}
.day-item.empty:hover { transform: none; box-shadow: none; border-color: transparent; }

.item-text { font-size: 14px; color: var(--text); line-height: 1.5; }

/* 活动逐条进入动画 */
.activity-enter-active {
  transition: all 0.35s ease;
}
.activity-enter-from { opacity: 0; transform: translateY(12px); }
.activity-move { transition: transform 0.3s ease; }

/* ===== 原始内容 ===== */
.raw-toggle {
  margin: 0 24px 16px;
  border-top: 1px solid var(--border);
}
.raw-summary {
  list-style: none;
  display: flex; align-items: center; justify-content: center;
  padding: 10px 0; cursor: pointer;
  font-size: 13px; color: var(--text-light);
  transition: color 0.2s;
  user-select: none;
}
.raw-summary::-webkit-details-marker { display: none; }
.raw-summary:hover { color: var(--primary); }
.raw-body {
  padding: 12px 16px; font-size: 13px; line-height: 1.8;
  color: var(--text-secondary);
  background: var(--bg); border-radius: var(--radius-sm);
  max-height: 300px; overflow-y: auto;
  white-space: pre-wrap; word-break: break-word;
}

/* ===== 空状态 ===== */
.empty-state {
  text-align: center; padding: 80px 20px; color: var(--text-secondary);
}
.empty-icon {
  font-size: 72px; display: block; margin-bottom: 16px;
  animation: float 3s ease-in-out infinite;
}
@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

/* ===== 响应式 ===== */
@media (max-width: 768px) {
  .plan-cover-content { padding: 14px 16px; }
  .plan-cover-icon { font-size: 28px; }
  .plan-city-name { font-size: 17px; }
  .day-tabs-bar { padding: 12px 16px 0; }
  .day-tab { padding: 6px 12px 6px 10px; font-size: 12px; }
  .day-tab-num { width: 20px; height: 20px; font-size: 10px; }
  .day-content-area { padding: 12px 16px; }
  .day-panel-title { font-size: 15px; }
  .day-item { padding: 8px 12px; }
  .raw-toggle { margin: 0 16px 12px; }
}
</style>
