# 🌍 游伴AI — 智能旅行规划助手

> **AI 驱动的个性化旅行规划系统**  
> 基于 Spring AI + Vue 3 构建，通过自然语言交互定制专属旅行计划

---

## 📖 目录

- [项目概述](#项目概述)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [路由与页面](#路由与页面)
- [AI 对话与工具调用](#ai-对话与工具调用)
- [API 接口](#api-接口)
- [数据模型](#数据模型)
- [配置说明](#配置说明)
- [常见问题](#常见问题)
- [扩展方向](#扩展方向)

---

## 项目概述

**游伴AI**（Travel Buddy AI）是一个基于大语言模型的智能旅行规划系统。用户通过自然语言对话，让 AI 助手完成从目的地推荐、景点查询、酒店推荐到完整行程生成的全流程旅行规划。

### 核心亮点

- **🤖 AI 驱动** — 接入 DeepSeek 大模型（兼容 OpenAI 协议），利用 Function Calling 自动调用旅游工具
- **💬 流式对话** — SSE（Server-Sent Events）实时流式响应 + 打字机逐字输出 + 思考阶段动画
- **🗺️ 完整行程生成** — AI 根据目的地、天数、偏好自动生成包含每日活动安排的详细行程
- **📱 精美前端** — Vue 3 SPA，沉浸式视频背景、毛玻璃效果、3D 卡片动效、骨架屏加载
- **🔐 用户系统** — JWT 身份认证、用户资料管理和旅行偏好设置
- **💾 行程管理** — 一键保存 AI 生成的行程，支持按天查看详情

---

## 功能特性

### 1. 🏠 首页 — 探索世界
- 全屏视频背景带来沉浸式视觉体验
- 搜索框支持快速输入旅行需求
- 一键跳转至 AI 规划、发现目的地、我的行程等功能模块
- 显示 AI 驱动标识（Powered by DeepSeek）

### 2. 💬 AI 智能规划 — 核心功能
- **多轮对话** — 与 AI 旅行助手自然交流，支持上下文记忆
- **工具调用** — AI 自动判断用户意图，调用景点查询、酒店推荐、天气查询、行程生成等工具
- **打字机效果** — SSE 流式接收 + 动态速度逐字输出，首批 Token 到达后自动关闭思考动画
- **Markdown 渲染** — AI 回复实时渲染成格式化内容
- **历史记录** — 查看和加载历史对话记录
- **行程保存** — 一键将 AI 生成的行程保存到「我的行程」

### 3. 🗺️ 发现目的地
- 10 大分类筛选（国内、国外、自然风光、城市漫游、海滨度假、美食之旅、文化历史等）
- 16 个精美目的地展示卡片，支持 3D 鼠标追踪动效
- 搜索与热门标签快速筛选
- 个性化推荐模块，根据旅行偏好智能匹配
- 特色推荐横幅（海岛奇遇等主题）
- **目的地详情页** — 沉浸式轮播画廊、深度介绍、景点清单、酒店推荐、实用贴士

### 4. ✨ 旅行灵感
- 灵感分类导航（海岛度假、美食之旅、摄影之旅、治愈之旅）
- 热门文章排行榜
- 精选专题（春日赏花、秋日红叶、冬日雪境）
- 瀑布流文章卡片布局
- 用户评价轮播（自动播放）
- AI 偏好推荐：选择兴趣标签，AI 生成个性化推荐
- **文章详情页** — 深度阅读体验，图文混排，相关推荐

### 5. 📋 我的行程
- 保存的旅行计划列表，每条显示目的地、天数和创建时间
- 渐变色封面 + 城市图标
- 展开查看每日详细安排（Day 选项卡切换 + 滑动动画）
- 活动项智能图标匹配（美食 🍽️、酒店 🏨、购物 🛍️ 等）
- 查看原始 Markdown 内容
- 删除行程

### 6. 📋 历史对话
- 浏览所有历史对话记录
- 逐条删除或一键清空

### 7. 👤 个人中心
- 用户信息展示与编辑（昵称、头像）
- 账户信息概览（用户名、注册时间、行程数量、账户状态）
- **旅行偏好设置**：
  - 🧘 出行风格（休闲度假 / 探险户外 / 文化历史 / 美食之旅 / 购物休闲）
  - 💰 预算范围（经济实惠 / 适中舒适 / 高端品质）
  - 🌸 偏好季节
  - 👨‍👩‍👧‍👦 同行人员（独自 / 情侣 / 家庭 / 朋友）
- **统计面板** — 行程数量、出行风格、注册时间、预算偏好（基于 Chart.js 可视化）

### 8. 🔐 用户认证
- 注册 / 登录（JWT Token 认证）
- Token 自动管理（localStorage 持久化 + Axios 拦截器自动附加）
- 路由守卫（未登录自动跳转登录页）
- 401 自动重定向

---

## 技术栈

### 后端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 3.2.0 |
| AI 框架 | Spring AI | 1.0.0-M5 |
| 大模型 | DeepSeek（deepseek-v4-flash） | OpenAI 兼容协议 |
| 数据库 | H2（内存数据库） | — |
| ORM | Spring Data JPA / Hibernate | — |
| 安全 | Spring Security + JWT（jjwt 0.11.5） | — |
| 构建 | Maven | 3.6+ |
| Java | OpenJDK | 17+ |

### 前端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Vue 3（Composition API + `<script setup>`） | ^3.4.0 |
| 构建 | Vite | 5.x |
| 路由 | Vue Router 4（Hash 模式） | ^4.2.0 |
| 状态管理 | Pinia | ^2.1.0 |
| HTTP | Axios | ^1.6.0 |
| Markdown | marked | ^9.1.0 |
| 图表 | Chart.js + vue-chartjs | ^4.5.1 / ^5.3.3 |

---

## 快速开始

### 环境要求

- **Java 17+**（OpenJDK）
- **Node.js 18+**（仅前端构建时需要）
- **Maven 3.6+**
- **API Key** — DeepSeek 或其他兼容 OpenAI 协议的 LLM

### 🚀 启动后端

```bash
# 1. 进入项目根目录
cd Travel

# 2. 配置 API Key（环境变量）
# Windows PowerShell:
$env:ANTHROPIC_AUTH_TOKEN="your-api-key-here"
# macOS / Linux:
export ANTHROPIC_AUTH_TOKEN="your-api-key-here"

# 3. 编译并启动
mvn clean install -DskipTests
mvn spring-boot:run
```

后端默认启动在 **`http://localhost:8081`**。

### 🚀 启动前端（开发模式）

```bash
# 1. 进入前端目录
cd frontend

# 2. 安装依赖
npm install

# 3. 启动开发服务器
npm run dev
```

前端默认启动在 **`http://localhost:3000`**，自动代理 `/api` 请求到后端 `8081` 端口。

### 📦 构建前端（生产模式）

```bash
cd frontend
npm run build
```

构建产物输出到 `frontend/dist/` 目录。

### 访问应用

打开浏览器访问 `http://localhost:3000` 即可。

---

## 项目结构

```
Travel/
├── pom.xml                                  # Maven 构建文件（根目录）
├── .gitignore
├── README.md                                # 本说明书
│
├── after/                                   # Spring Boot 后端
│   └── src/main/
│       ├── java/com/study/travel/
│       │   ├── TravelApplication.java       # 应用入口
│       │   ├── config/
│       │   │   ├── AIConfig.java            # ChatClient + System Prompt + ChatMemory
│       │   │   ├── SecurityConfig.java      # JWT 安全配置
│       │   │   └── WebConfig.java           # CORS 跨域配置
│       │   ├── controller/
│       │   │   ├── AuthController.java      # 注册/登录接口
│       │   │   ├── ChatController.java      # 聊天/历史/行程接口
│       │   │   └── ProfileController.java   # 用户信息接口
│       │   ├── dto/                         # 数据传输对象
│       │   │   ├── ApiResult.java
│       │   │   ├── AuthResponse.java
│       │   │   ├── ChatRequest.java
│       │   │   ├── ChatResponse.java
│       │   │   ├── LoginRequest.java
│       │   │   ├── PlanSaveRequest.java
│       │   │   ├── ProfileUpdateRequest.java
│       │   │   └── RegisterRequest.java
│       │   ├── entity/                      # JPA 实体
│       │   │   ├── ChatMessage.java
│       │   │   ├── TravelPlan.java
│       │   │   └── User.java
│       │   ├── repository/                  # 数据访问层
│       │   │   ├── ChatMessageRepository.java
│       │   │   ├── TravelPlanRepository.java
│       │   │   └── UserRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java       # JWT 认证过滤器
│       │   │   └── JwtUtil.java             # JWT 工具类
│       │   ├── service/
│       │   │   ├── AgentService.java        # AI 对话编排服务
│       │   │   └── UserService.java         # 用户服务
│       │   └── tool/
│       │       ├── ToolRegistry.java        # AI 工具注册中心（4 个 FunctionCallback）
│       │       └── TravelDataService.java   # 模拟旅游数据源（10 城市）
│       └── resources/
│           └── application.properties       # 应用配置
│
└── frontend/                                # Vue 3 前端
    ├── index.html                           # HTML 入口
    ├── package.json                         # 依赖清单
    ├── vite.config.js                       # Vite 配置 + API 代理
    ├── dist/                                # 构建产物
    └── src/
        ├── main.js                          # Vue 应用入口
        ├── App.vue                          # 根组件
        ├── api/index.js                     # Axios 实例 + 拦截器
        ├── stores/auth.js                   # Pinia 认证状态管理
        ├── router/index.js                  # 路由配置（Hash 模式）
        ├── components/
        │   ├── AppLayout.vue                # 应用布局（侧边栏导航）
        │   └── AiIcon.vue                   # AI 图标动画组件
        ├── composables/
        │   ├── useChat.js                   # SSE 流式对话 + 打字机效果
        │   ├── useToast.js                  # Toast 通知
        │   └── usePlanCharts.js             # 行程统计图表
        ├── data/
        │   ├── destinations.js              # 16 个目的地深度数据
        │   ├── articles.js                  # 灵感文章数据
        │   └── images.js                    # 图片工具函数（pexels / unsplash）
        ├── styles/main.css                  # 全局样式 + CSS 变量
        ├── assets/
        │   ├── logo-full.svg                # 完整 Logo
        │   └── logo-icon.svg                # Logo 图标
        └── views/
            ├── HomeView.vue                 # 🏠 首页
            ├── LoginView.vue                # 🔐 登录/注册
            ├── ChatView.vue                 # 💬 AI 智能规划（核心）
            ├── ExploreView.vue              # 🗺️ 发现目的地
            ├── DestinationDetailView.vue    # 🏛️ 目的地详情
            ├── InspirationView.vue          # ✨ 旅行灵感
            ├── ArticleDetailView.vue        # 📄 文章详情
            ├── CollectionDetailView.vue     # 📚 专题合集
            ├── PlansView.vue                # 📋 我的行程
            ├── HistoryView.vue              # 📋 历史对话
            └── ProfileView.vue              # 👤 个人中心
```

---

## 路由与页面

| 路径 | 页面 | 说明 |
|------|------|------|
| `/` | HomeView | 首页，视频背景 + 搜索入口 |
| `/login` | LoginView | 登录/注册切换，玻璃拟态卡片 |
| `/app/chat` | ChatView | 💬 AI 智能规划（核心页面） |
| `/app/explore` | ExploreView | 🗺️ 发现目的地 |
| `/app/explore/destination/:name` | DestinationDetailView | 🏛️ 目的地详情 |
| `/app/inspiration` | InspirationView | ✨ 旅行灵感 |
| `/app/inspiration/article/:slug` | ArticleDetailView | 📄 文章详情 |
| `/app/inspiration/collection/:slug` | CollectionDetailView | 📚 专题合集 |
| `/app/plans` | PlansView | 📋 我的行程 |
| `/app/history` | HistoryView | 📋 历史对话 |
| `/app/profile` | ProfileView | 👤 个人中心 |

### 路由守卫

- `/app/*` 路径需登录认证，未登录自动跳转 `/login`
- 已登录用户访问 `/login` 或 `/` 自动跳转至 `/app/chat`
- 使用 `<script setup>` + 懒加载（`() => import(...)`）

---

## AI 对话与工具调用

### 系统提示词

AI 旅行助手的角色定义在 `AIConfig.java` 中：

> - 角色：专业 AI 旅游规划助手「旅行小助手」
> - 用户提到目的地+天数 → 调用 `generateTravelPlan`
> - 用户问天气 → 调用 `getWeather`
> - 用户问景点 → 调用 `getScenicSpots`
> - 用户问酒店 → 调用 `getHotels`
> - 回复使用适当的 emoji 和 Markdown 格式
> - 用户未明确目的地时主动推荐热门城市
> - 记住用户偏好，在多轮对话中保持一致

### 注册的 AI 工具（Function Calling）

| 工具名称 | 描述 | 输入参数 |
|---------|------|---------|
| `getScenicSpots` | 查询城市景点列表 | `{ city: string }` |
| `getHotels` | 按预算推荐酒店 | `{ city: string, budget?: "经济型"\|"舒适型"\|"品质型"\|"奢华型" }` |
| `getWeather` | 查询模拟天气 | `{ city: string }` |
| `generateTravelPlan` | 生成完整行程 | `{ city: string, days: number, preferences?: string }` |

### 对话交互流程

```
用户输入消息
    │
    ▼
POST /api/chat/stream (SSE)
    │
    ▼
AI 分析意图 → 调用对应工具 → 整合结果生成回复
    │
    ▼
逐字符 SSE 推送到前端 → 思考动画关闭 → 打字机逐字输出
    │
    ▼
marked 渲染 Markdown → 用户可点击「💾 保存行程」
```

### 打字机效果（useChat.js）

- SSE 流式接收 AI 回复，逐字追加到 `streamFullContent`
- 定时器以 **动态速度** 从 `streamDisplayContent` 向用户展现（缓冲区越大，步长越大）
- 首批 Token 到达后自动关闭「思考中」动画
- 支持用户手动「显示全部」跳过打字机

### 模拟旅游数据

内置 **10 个城市**的模拟数据（每个城市 6 个景点 + 3 家酒店）：

| 城市 | 代表景点 | 代表酒店 |
|------|---------|---------|
| 🏯 北京 | 故宫、长城、颐和园 | 希尔顿、四合院民宿、如家 |
| 🌃 上海 | 外滩、东方明珠、迪士尼 | 和平饭店、文华东方、全季 |
| 🌊 杭州 | 西湖、灵隐寺、龙井村 | 西湖国宾馆、法云安缦、汉庭 |
| 🐼 成都 | 大熊猫基地、宽窄巷子、都江堰 | 博舍、成都院子、锦江之星 |
| 🏖️ 三亚 | 亚龙湾、蜈支洲岛、天涯海角 | 亚特兰蒂斯、艾迪逊、海景公寓 |
| 🏛️ 西安 | 兵马俑、大雁塔、不夜城 | 索菲特传奇、W 酒店、汉庭 |
| 🗼 东京 | 浅草寺、东京塔、秋叶原 | 半岛酒店、格兰贝尔、民宿 |
| 🥐 巴黎 | 埃菲尔铁塔、卢浮宫、凡尔赛宫 | 丽兹酒店、铂尔曼、宜必思 |
| 🏘️ 丽江 | 古城、玉龙雪山、泸沽湖 | 悦榕庄、英迪格、花间堂 |
| 🌊 厦门 | 鼓浪屿、厦大、环岛路 | 康莱德、鼓浪屿民宿、如家 |

---

## API 接口

所有接口统一前缀 `/api`。认证接口无需 Token，其余需 `Authorization: Bearer <token>`。

### 统一响应格式

```json
{ "code": 200, "message": "success", "data": { ... } }
```

### 🔐 认证

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/register` | 注册 `{ username, password }` |
| POST | `/api/auth/login` | 登录 `{ username, password }` → 返回 `{ token, username, nickname }` |

### 💬 聊天（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/chat/stream` | 流式对话（SSE），`{ message }` → `Flux<String>` |
| POST | `/api/chat` | 同步对话，`{ message }` → 完整 AI 回复 |
| GET | `/api/chat/history` | 获取当前用户聊天记录 |
| DELETE | `/api/chat/history/{id}` | 删除单条记录 |
| DELETE | `/api/chat/history` | 清空所有记录 |

### 📋 行程（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/chat/plan/save` | 保存行程 `{ city, days, content, preferences }` |
| GET | `/api/chat/plan/list` | 获取行程列表 |
| DELETE | `/api/chat/plan/{id}` | 删除行程 |

### 👤 用户（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/user/profile` | 获取用户信息 |
| PUT | `/api/user/profile` | 更新资料 `{ nickname, preferences }` |

---

## 数据模型

### User（用户）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| username | String (unique) | 用户名 |
| password | String | BCrypt 加密密码 |
| nickname | String (nullable) | 昵称 |
| avatar | String (nullable) | 头像 URL |
| createTime | LocalDateTime | 注册时间 |

### TravelPlan（旅行计划）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| userId | Long | 用户 ID |
| city | String | 目的地城市 |
| days | Integer | 旅行天数 |
| content | Text | AI 生成的 Markdown 行程内容 |
| preferences | String (nullable) | 用户偏好 |
| createTime | LocalDateTime | 创建时间 |

### ChatMessage（聊天消息）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| userId | Long | 用户 ID |
| role | String | "user" 或 "assistant" |
| content | Text | 消息内容 |
| createTime | LocalDateTime | 发送时间 |

---

## 配置说明

### application.properties

```properties
# 服务器端口
server.port=8081

# H2 内存数据库
spring.datasource.url=jdbc:h2:mem:travel_agent;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false

# JWT（生产环境请更换密钥）
app.jwt.secret=TravelAssistant2024SecretKeyForJWTTokenGenerationAndValidation
app.jwt.expiration=86400000    # 24 小时

# LLM（OpenAI 兼容协议）
spring.ai.openai.base-url=https://api.deepseek.com
spring.ai.openai.api-key=${ANTHROPIC_AUTH_TOKEN}
spring.ai.openai.chat.options.model=deepseek-v4-flash
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.chat.options.max-tokens=2000
```

### 更换 LLM 提供商

```properties
# 通义千问示例
spring.ai.openai.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
spring.ai.openai.api-key=your-dashscope-api-key
spring.ai.openai.chat.options.model=qwen-plus

# GPT 示例
spring.ai.openai.base-url=https://api.openai.com/v1
spring.ai.openai.api-key=your-openai-api-key
spring.ai.openai.chat.options.model=gpt-4o
```

### 前端 API 代理（vite.config.js）

```js
proxy: {
  '/api': {
    target: 'http://localhost:8081',   // 后端地址
    changeOrigin: true
  }
}
```

---

## 常见问题

### Q: 启动后端报错 "API Key not configured"？
A: 设置环境变量 `ANTHROPIC_AUTH_TOKEN`，或在 `application.properties` 中直接填入 API Key。

### Q: 是否必须联网？
A: AI 对话需调用 DeepSeek API，必须联网。首页浏览、目的地发现等静态页面可离线使用。

### Q: 数据会丢失吗？
A: 使用 H2 内存数据库，重启后数据丢失。如需持久化，替换为 MySQL/PostgreSQL：
1. 添加对应数据库驱动依赖
2. 修改 `spring.datasource.url` 等配置
3. 将 `ddl-auto` 设为 `update` 或使用 Flyway 管理迁移

### Q: 如何替换为真实 API 数据？
A: `TravelDataService.java` 中的景点、酒店、天气数据为模拟数据。可在该类中调用第三方旅游 API（携程、高德、OpenWeather）替代静态数据。

### Q: 如何增加新的支持城市？
A: 在 `TravelDataService.java` 的静态代码块中按现有格式添加景点和酒店数据。前端目的地数据在 `frontend/src/data/destinations.js` 中添加对应条目。

### Q: 前端构建报错？
A: 确保 Node.js ≥ 18，运行 `npm install`。如有缓存问题，删除 `node_modules` 和 `package-lock.json` 后重新安装。

---

## 扩展方向

- [ ] **数据库持久化** — 替换 H2 为 MySQL/PostgreSQL
- [ ] **真实旅游 API** — 接入高德地图、携程、OpenWeather 等数据源
- [ ] **Redis 对话缓存** — 替代内存 ChatMemory，支持分布式
- [ ] **文件上传** — 支持用户上传头像
- [ ] **社交功能** — 行程分享、用户评论、游记发布
- [ ] **移动端适配** — PWA 或原生 App 封装
- [ ] **多语言 i18n** — 国际化支持
- [ ] **行程导出** — PDF / 图片格式导出
- [ ] **OAuth 登录** — 微信 / Google 第三方登录
- [ ] **消息通知** — 天气变化提醒、出行前提醒

---

## 开源许可

本项目仅用于学习和交流目的。

---

*Made with ❤️ by 游伴AI Team*
