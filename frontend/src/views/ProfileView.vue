<template>
  <div class="page-container">
    <div class="page-header">
      <h2>👤 个人中心</h2>
      <p>管理个人信息与旅行偏好</p>
    </div>

    <!-- 个人信息卡片 -->
    <div class="profile-section">
      <div class="profile-section-header">
        <h3>📋 基本信息</h3>
      </div>
      <div class="profile-card">
        <div class="profile-avatar-section">
          <div class="profile-avatar">{{ displayName.charAt(0).toUpperCase() }}</div>
          <div class="profile-name">{{ displayName }}</div>
        </div>

        <div class="profile-fields">
          <div class="form-group">
            <label>👤 用户名</label>
            <input :value="profile.username" disabled />
          </div>
          <div class="form-group">
            <label>😊 昵称</label>
            <div class="field-row">
              <input v-model="nickname" :disabled="!editing" placeholder="给自己取个好听的昵称" />
              <button v-if="!editing" class="btn btn-text btn-sm" @click="editing = true">✏️ 编辑</button>
            </div>
          </div>
          <div v-if="editing" class="form-actions">
            <button class="btn btn-primary" @click="saveProfile" :disabled="saving" style="width:auto;">
              {{ saving ? '保存中...' : '💾 保存资料' }}
            </button>
            <button class="btn btn-outline" @click="cancelEdit" style="width:auto;">取消</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 旅行偏好卡片 -->
    <div class="profile-section">
      <div class="profile-section-header">
        <h3>🎯 旅行偏好</h3>
        <p>帮助我们为你推荐更合适的旅行方案</p>
      </div>
      <div class="profile-card">
        <div class="pref-grid">
          <!-- 出行风格 -->
          <div class="pref-group">
            <label>出行风格</label>
            <div class="pref-options">
              <button
                v-for="style in travelStyles"
                :key="style.value"
                class="pref-btn"
                :class="{ active: preferences.style === style.value }"
                @click="preferences.style = style.value"
              >
                {{ style.icon }} {{ style.label }}
              </button>
            </div>
          </div>

          <!-- 预算范围 -->
          <div class="pref-group">
            <label>预算范围</label>
            <div class="pref-options">
              <button
                v-for="b in budgets"
                :key="b.value"
                class="pref-btn"
                :class="{ active: preferences.budget === b.value }"
                @click="preferences.budget = b.value"
              >
                {{ b.label }}
              </button>
            </div>
          </div>

          <!-- 偏好季节 -->
          <div class="pref-group">
            <label>偏好季节</label>
            <div class="pref-options">
              <button
                v-for="s in seasons"
                :key="s.value"
                class="pref-btn"
                :class="{ active: preferences.season === s.value }"
                @click="preferences.season = s.value"
              >
                {{ s.icon }} {{ s.label }}
              </button>
            </div>
          </div>

          <!-- 同行人数 -->
          <div class="pref-group">
            <label>常与谁同行</label>
            <div class="pref-options">
              <button
                v-for="c in companions"
                :key="c.value"
                class="pref-btn"
                :class="{ active: preferences.companion === c.value }"
                @click="preferences.companion = c.value"
              >
                {{ c.icon }} {{ c.label }}
              </button>
            </div>
          </div>
        </div>

        <div class="form-actions" style="margin-top:16px;">
          <button class="btn btn-primary" @click="savePreferences" :disabled="prefSaving" style="width:auto;">
            {{ prefSaving ? '保存中...' : '💾 保存偏好' }}
          </button>
        </div>
      </div>
    </div>

    <!-- 注册信息 -->
    <div class="profile-section">
      <div class="profile-section-header">
        <h3>📅 账户信息</h3>
      </div>
      <div class="profile-card">
        <div class="profile-info-row">
          <span>注册时间</span>
          <span class="info-value">{{ profile.createTime || '未知' }}</span>
        </div>
        <div class="profile-info-row">
          <span>已保存行程</span>
          <span class="info-value">{{ planCount }} 个</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '../api'
import { useAuthStore } from '../stores/auth'
import { useToast } from '../composables/useToast'

const auth = useAuthStore()
const toast = useToast()

const displayName = computed(() => auth.displayName)
const profile = ref({ username: '', nickname: '', createTime: '' })
const nickname = ref('')
const editing = ref(false)
const saving = ref(false)
const planCount = ref(0)

// 旅行偏好
const prefSaving = ref(false)
const preferences = ref({
  style: 'relax',
  budget: 'medium',
  season: 'all',
  companion: 'alone'
})

const travelStyles = [
  { value: 'relax', icon: '🧘', label: '休闲度假' },
  { value: 'adventure', icon: '🧗', label: '探险户外' },
  { value: 'culture', icon: '🏛️', label: '文化历史' },
  { value: 'food', icon: '🍜', label: '美食之旅' },
  { value: 'shopping', icon: '🛍️', label: '购物休闲' }
]

const budgets = [
  { value: 'budget', label: '💰 经济实惠' },
  { value: 'medium', label: '💵 适中舒适' },
  { value: 'luxury', label: '💎 高端品质' }
]

const seasons = [
  { value: 'spring', icon: '🌸', label: '春季' },
  { value: 'summer', icon: '☀️', label: '夏季' },
  { value: 'autumn', icon: '🍁', label: '秋季' },
  { value: 'winter', icon: '❄️', label: '冬季' },
  { value: 'all', icon: '🌟', label: '不限' }
]

const companions = [
  { value: 'alone', icon: '🚶', label: '独自' },
  { value: 'couple', icon: '💑', label: '情侣' },
  { value: 'family', icon: '👨‍👩‍👧‍👦', label: '家庭' },
  { value: 'friends', icon: '👫', label: '朋友' }
]

onMounted(() => load())

async function load() {
  try {
    const [profileRes, planRes] = await Promise.all([
      api.get('/api/user/profile'),
      api.get('/api/chat/plan/list').catch(() => ({ data: { data: [] } }))
    ])
    if (profileRes.data.code === 200) {
      profile.value = profileRes.data.data
      nickname.value = profile.value.nickname || ''
      // 加载已保存的偏好
      if (profile.value.preferences) {
        Object.assign(preferences.value, profile.value.preferences)
      }
    }
    planCount.value = planRes.data?.data?.length || 0
  } catch (e) { toast.error('加载失败') }
}

async function saveProfile() {
  saving.value = true
  try {
    const res = await api.put('/api/user/profile', { nickname: nickname.value })
    if (res.data.code === 200) {
      profile.value.nickname = nickname.value
      auth.updateProfile(nickname.value)
      editing.value = false
      toast.success('✅ 资料已保存')
    } else {
      toast.error(res.data.message || '保存失败')
    }
  } catch (e) { toast.error('保存失败') }
  saving.value = false
}

function cancelEdit() {
  nickname.value = profile.value.nickname || ''
  editing.value = false
}

async function savePreferences() {
  prefSaving.value = true
  try {
    const res = await api.put('/api/user/profile', {
      nickname: nickname.value,
      preferences: preferences.value
    })
    if (res.data.code === 200) {
      toast.success('✅ 旅行偏好已保存')
    } else {
      toast.error(res.data.message || '保存失败')
    }
  } catch (e) { toast.error('保存失败') }
  prefSaving.value = false
}
</script>

<style scoped>
/* 分段布局 */
.profile-section {
  margin-bottom: 24px;
}

.profile-section-header {
  margin-bottom: 12px;
}

.profile-section-header h3 {
  font-size: 17px;
  font-weight: 700;
  color: var(--text);
  margin: 0;
}

.profile-section-header p {
  font-size: 13px;
  color: var(--text-secondary);
  margin: 2px 0 0;
}

/* 个人信息 */
.profile-avatar-section {
  text-align: center;
  margin-bottom: 24px;
}

.profile-avatar {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  background: var(--gradient-primary);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 8px;
}

.profile-name {
  font-size: 16px;
  font-weight: 600;
  color: var(--text);
}

.profile-fields {
  max-width: 400px;
  margin: 0 auto;
}

.field-row {
  display: flex;
  gap: 8px;
  align-items: center;
}

.field-row input {
  flex: 1;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 8px;
}

/* 偏好网格 */
.pref-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.pref-group label {
  display: block;
  font-size: 13px;
  font-weight: 600;
  color: var(--text);
  margin-bottom: 8px;
}

.pref-options {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.pref-btn {
  padding: 8px 16px;
  border: 1px solid var(--border);
  border-radius: 10px;
  background: #fff;
  color: var(--text-secondary);
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  font-family: inherit;
}

.pref-btn:hover {
  border-color: var(--primary-light);
  color: var(--primary);
}

.pref-btn.active {
  background: var(--gradient-primary);
  color: #fff;
  border-color: transparent;
}

/* 信息行 */
.profile-info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid var(--border);
  font-size: 14px;
  color: var(--text-secondary);
}

.profile-info-row:last-child {
  border-bottom: none;
}

.info-value {
  color: var(--text);
  font-weight: 600;
}
</style>
