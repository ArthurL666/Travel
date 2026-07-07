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
- [用户自定义 API Key](#用户自定义-api-key)
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
- **💬 流式对话** — SSE 实时流式响应 + 打字机逐字输出 + 思考阶段动画
- **🗺️ 完整行程生成** — AI 根据目的地、天数、偏好自动生成包含每日活动安排的详细行程
- **🔑 用户自配 API Key** — 在前端直接填入自己的 DeepSeek API Key，无需服务端配置
- **📱 精美前端** — Vue 3 SPA，毛玻璃效果、3D 卡片动效、沉浸式视觉体验
- **🔐 用户系统** — JWT 身份认证、用户资料管理和旅行偏好设置
- **💾 行程管理** — 一键保存 AI 生成的行程，支持按天查看详情

---

## 功能特性

### 1. 🏠 首页 — 探索世界
- 搜索框支持快速输入旅行需求
- 一键跳转至 AI 规划、发现目的地、我的行程等功能模块
- 显示 AI 驱动标识

### 2. 💬 AI 智能规划 — 核心功能
- **多轮对话** — 与 AI 旅行助手自然交流，支持上下文记忆
- **工具调用** — AI 自动判断用户意图，调用景点查询、酒店推荐、天气查询、行程生成等工具
- **打字机效果** — SSE 流式接收 + 动态速度逐字输出，首批 Token 到达后自动关闭思考动画
- **Markdown 渲染** — AI 回复实时渲染成格式化内容
- **历史记录** — 查看和加载历史对话记录
- **行程保存** — 一键将 AI 生成的行程保存到「我的行程」
- **🔑 API Key 自助配置** — 前端弹窗输入 DeepSeek API Key，仅存本地浏览器

### 3. 🗺️ 发现目的地
- 10 大分类筛选（国内、国外、自然风光、城市漫游、海滨度假、美食之旅、文化历史等）
- 16 个精美目的地展示卡片，支持 3D 鼠标追踪动效
- 搜索与热门标签快速筛选
- 个性化推荐模块，根据旅行偏好智能匹配
- **目的地详情页** — 沉浸式轮播画廊、深度介绍、景点清单、酒店推荐、实用贴士

### 4. ✨ 旅行灵感
- 灵感分类导航（海岛度假、美食之旅、摄影之旅、治愈之旅）
- 热门文章排行榜、精选专题
- 瀑布流文章卡片布局
- 用户评价轮播（自动播放）
- AI 偏好推荐：选择兴趣标签，AI 生成个性化推荐
- **文章/合集详情页** — 深度阅读体验，图文混排

### 5. 📋 我的行程
- 保存的旅行计划列表，每条显示目的地、天数和创建时间
- 渐变色封面 + 城市图标
- 展开查看每日详细安排（Day 选项卡切换 + 滑动动画）
- 活动项智能图标匹配（美食、酒店、购物等）
- 删除行程

### 6. 📋 历史对话
- 浏览所有历史对话记录
- 逐条删除或一键清空

### 7. 👤 个人中心
- 用户信息展示与编辑（昵称、头像）
- **旅行偏好设置**：出行风格、预算范围、偏好季节、同行人员
- **统计面板** — 行程数量、出行风格、注册时间、预算偏好（Chart.js 可视化）

### 8. 🔐 用户认证
- 注册 / 登录（JWT Token 认证）
- Token 自动管理（localStorage 持久化 + Axios 拦截器自动附加）
- 路由守卫（未登录自动跳转登录页）

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
- **DeepSeek API Key** — 在 [platform.deepseek.com](https://platform.deepseek.com/api_keys) 获取

### 🚀 启动后端

```bash
# 1. 进入项目根目录
cd Travel

# 2. 配置 API Key（环境变量，可选 — 用户也可在前端自行配置）
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
├── pom.xml                                  # Maven 构建文件
├── README.md                                # 本说明书
│
├── after/                                   # Spring Boot 后端
│   └── src/main/
│       ├── java/com/study/travel/
│       │   ├── TravelApplication.java       # 应用入口
│       │   ├── config/
│       │   │   ├── AIConfig.java            # ChatClient + SystemPrompt + ChatMemory
│       │   │   ├── SecurityConfig.java      # JWT 安全配置
│       │   │   └── WebConfig.java           # CORS 跨域配置
│       │   ├── controller/
│       │   │   ├── AuthController.java      # 注册/登录接口
│       │   │   ├── ChatController.java      # 聊天/历史/行程接口
│       │   │   └── ProfileController.java   # 用户信息接口
│       │   ├── dto/                         # 数据传输对象
│       │   │   ├── ChatRequest.java         # ↑ 新增 apiKey 字段
│       │   │   └── ...
│       │   ├── entity/                      # JPA 实体
│       │   ├── repository/                  # 数据访问层
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java
│       │   │   └── JwtUtil.java
│       │   ├── service/
│       │   │   ├── AgentService.java        # ↑ 支持动态 API Key 创建 ChatClient
│       │   │   └── UserService.java
│       │   └── tool/
│       │       ├── ToolRegistry.java        # 4 个 FunctionCallback 工具
│       │       └── TravelDataService.java   # 10 城市模拟数据
│       └── resources/
│           └── application.properties       # 服务端配置
│
└── frontend/                                # Vue 3 前端
    ├── index.html
    ├── package.json
    ├── vite.config.js                       # ↑ API 代理
    └── src/
        ├── main.js
        ├── App.vue
        ├── api/index.js                     # Axios 实例 + 拦截器
        ├── stores/auth.js                   # Pinia 认证
        ├── router/index.js                  # Hash 路由 + 守卫
        ├── components/
        │   ├── AppLayout.vue
        │   └── AiIcon.vue
        ├── composables/
        │   ├── useChat.js                   # ↑ SSE 流式 + 携带 apiKey
        │   ├── useToast.js
        │   └── usePlanCharts.js
        ├── data/
        │   ├── destinations.js
        │   ├── articles.js
        │   └── images.js
        ├── styles/main.css
        └── views/
            ├── HomeView.vue                 # 🏠 首页
            ├── LoginView.vue                # 🔐 登录/注册
            ├── ChatView.vue                 # 💬 AI 规划 ↑ API 配置弹窗
            ├── ExploreView.vue              # 🗺️ 发现目的地
            ├── DestinationDetailView.vue
            ├── InspirationView.vue          # ✨ 旅行灵感
            ├── ArticleDetailView.vue
            ├── CollectionDetailView.vue
            ├── PlansView.vue                # 📋 我的行程
            ├── HistoryView.vue
            └── ProfileView.vue              # 👤 个人中心
```

> `↑` 标记了新增/修改的文件

---

## 路由与页面

| 路径 | 页面 | 说明 |
|------|------|------|
| `/` | HomeView | 首页，搜索入口 |
| `/login` | LoginView | 登录/注册切换，玻璃拟态卡片 |
| `/app/chat` | ChatView | 💬 AI 智能规划（核心页面）|
| `/app/explore` | ExploreView | 🗺️ 发现目的地 |
| `/app/explore/destination/:name` | DestinationDetailView | 🏛️ 目的地详情 |
| `/app/inspiration` | InspirationView | ✨ 旅行灵感 |
| `/app/inspiration/article/:slug` | ArticleDetailView | 📄 文章详情 |
| `/app/inspiration/collection/:slug` | CollectionDetailView | 📚 专题合集 |
| `/app/plans` | PlansView | 📋 我的行程 |
| `/app/history` | HistoryView | 📋 历史对话 |
| `/app/profile` | ProfileView | 👤 个人中心 |

**路由守卫**：`/app/*` 需登录认证，未登录自动跳转 `/login`。

---

## AI 对话与工具调用

### 架构说明

```
┌─────────────┐     POST /api/chat/stream      ┌──────────────────┐
│   Vue 3     │ ──── { message, apiKey } ────→  │ Spring Boot      │
│   Frontend  │ ←─── SSE (text/event-stream) ─── │ ChatController   │
│             │       data: 逐字内容              └────────┬─────────┘
│  localStorage│                                         │
│  deepseekApiKey│                                        ▼
│             │                                  ┌──────────────────┐
└─────────────┘                                  │  AgentService    │
                                                  │                  │
                                                  │  createChatClient│
                                                  │  (apiKey)        │
                                                  │       │          │
                                                  │  ┌────▼──────┐   │
                                                  │  │ OpenAiApi  │   │
                                                  │  │ DeepSeek   │   │
                                                  │  └───────────┘   │
                                                  └──────────────────┘
```

- **无 apiKey** → 使用服务端配置的 ChatClient（`application.properties` 中的默认 Key）
- **有 apiKey** → 后端动态创建 `OpenAiChatModel`，构建独立 ChatClient 实例
- 两种模式均保留完整的系统提示、对话记忆和工具调用能力

### 系统提示词

AI 旅行助手的角色定义在 `AIConfig.java` 的 `SYSTEM_PROMPT` 常量中：

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
| `getHotels` | 按预算推荐酒店 | `{ city, budget? }` |
| `getWeather` | 查询模拟天气 | `{ city: string }` |
| `generateTravelPlan` | 生成完整行程 | `{ city, days, preferences? }` |

### 模拟旅游数据

内置 **10 个城市**的模拟数据（每个城市 6 个景点 + 3 家酒店）：北京、上海、杭州、成都、三亚、西安、东京、巴黎、丽江、厦门。

---

## 用户自定义 API Key

用户可以在前端直接配置自己的 DeepSeek API Key，无需依赖服务端环境变量。

### 使用方式

1. 进入 **AI 规划** 聊天页面
2. 点击顶部右侧的 **API** 按钮（锁图标 🔒）
3. 在弹出的对话框中输入你的 DeepSeek API Key
4. 点击「保存」— Key 将存储在浏览器 `localStorage` 中
5. 配置成功后按钮旁显示绿色 **●** 指示点

### 安全说明

- 用户 API Key **仅保存在本地浏览器**（`localStorage`），不上传至任何第三方服务器
- 每次发送消息时，Key 通过 HTTPS 请求体中的 `apiKey` 字段传递至后端
- 后端在内存中使用该 Key 创建临时 ChatClient，**不落盘、不记录日志**
- 用户可随时点击「清除 Key」移除

### 两种模式对比

| 模式 | 配置方式 | 适用场景 |
|------|---------|---------|
| 服务端默认 | `application.properties` / 环境变量 | 部署者预先配置共享 Key |
| 用户自定义 | 前端弹窗输入 | 用户使用自己的 Key，互不影响 |

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
| POST | `/api/auth/register` | 注册 `{ username, password, nickname? }` |
| POST | `/api/auth/login` | 登录 `{ username, password }` → 返回 token |

### 💬 聊天（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/chat/stream` | 流式对话（SSE），`{ message, apiKey? }` → `Flux<String>` |
| POST | `/api/chat` | 同步对话，`{ message, apiKey? }` → 完整 AI 回复 |
| GET | `/api/chat/history` | 获取当前用户聊天记录 |
| DELETE | `/api/chat/history/{id}` | 删除单条记录 |
| DELETE | `/api/chat/history` | 清空所有记录 |

> `apiKey` 为可选字段，不传则使用服务端配置的默认 Key。

### 📋 行程（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/chat/plan/save` | 保存行程 `{ city, days, content, preferences? }` |
| GET | `/api/chat/plan/list` | 获取行程列表 |
| DELETE | `/api/chat/plan/{id}` | 删除行程 |

### 👤 用户（需认证）

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/user/profile` | 获取用户信息 |
| PUT | `/api/user/profile` | 更新资料 `{ nickname, preferences? }` |

---

## 数据模型

### User（用户）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| username | String (unique) | 用户名 |
| password | String | BCrypt 加密密码 |
| nickname | String (nullable) | 昵称 |
| createTime | LocalDateTime | 注册时间 |

### TravelPlan（旅行计划）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| userId | Long | 用户 ID |
| city | String | 目的地城市 |
| days | Integer | 旅行天数 |
| content | Text | AI 生成的 Markdown 行程 |
| preferences | String (nullable) | 用户偏好 |
| createTime | LocalDateTime | 创建时间 |

### ChatMessage（聊天消息）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键 |
| userId | Long | 用户 ID |
| role | String | "user" 或 "assistant" |
| content | Text | 消息内容 |
| createTime | LocalDateTime | 发送时间 |

---

## 配置说明

### application.properties

```properties
server.port=8081

# H2 内存数据库
spring.datasource.url=jdbc:h2:mem:travel_agent;DB_CLOSE_DELAY=-1
spring.h2.console.enabled=true

# JPA
spring.jpa.hibernate.ddl-auto=update

# JWT（生产环境请更换密钥）
app.jwt.secret=TravelAssistant2024SecretKey...
app.jwt.expiration=86400000

# LLM（OpenAI 兼容协议）— 用户也可在前端自行配置 Key
spring.ai.openai.base-url=https://api.deepseek.com
spring.ai.openai.api-key=${ANTHROPIC_AUTH_TOKEN}
spring.ai.openai.chat.options.model=deepseek-v4-flash
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.chat.options.max-tokens=2000
```

### 更换 LLM 提供商

```properties
# 通义千问
spring.ai.openai.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
spring.ai.openai.chat.options.model=qwen-plus

# OpenAI GPT
spring.ai.openai.base-url=https://api.openai.com/v1
spring.ai.openai.chat.options.model=gpt-4o
```

### 前端 API 代理（vite.config.js）

```js
proxy: { '/api': { target: 'http://localhost:8081', changeOrigin: true } }
```

---

## 常见问题

### Q: 启动后端报错 "API Key not configured"？
A: 设置环境变量 `ANTHROPIC_AUTH_TOKEN`，或直接在 `application.properties` 中填入 Key。用户也可略过服务端配置，在前端直接输入自己的 Key。

### Q: 如何在前端配置自己的 API Key？
A: 进入 AI 规划聊天页 → 点击顶部 **API** 按钮 → 输入 DeepSeek API Key → 保存。Key 仅存储在浏览器中。

### Q: 是否必须联网？
A: AI 对话需调用 DeepSeek API，必须联网。首页浏览、目的地发现等静态页面可离线使用。

### Q: 数据会丢失吗？
A: 使用 H2 内存数据库，重启后数据丢失。如需持久化，替换为 MySQL/PostgreSQL。

### Q: 如何替换为真实 API 数据？
A: `TravelDataService.java` 中的景点、酒店、天气数据为模拟数据。可在该类中调用第三方旅游 API 替代静态数据。

### Q: 如何增加新的支持城市？
A: 在 `TravelDataService.java` 的静态代码块中按现有格式添加景点和酒店数据。前端目的地数据在 `frontend/src/data/destinations.js` 中添加对应条目。

---

## 扩展方向

- [ ] **数据库持久化** — 替换 H2 为 MySQL/PostgreSQL
- [ ] **真实旅游 API** — 接入高德地图、携程、OpenWeather 等数据源
- [ ] **Redis 对话缓存** — 替代内存 ChatMemory，支持分布式
- [ ] **文件上传** — 支持用户上传头像
- [ ] **社交功能** — 行程分享、用户评论、游记发布
- [ ] **多语言 i18n** — 国际化支持
- [ ] **行程导出** — PDF / 图片格式导出
- [ ] **OAuth 登录** — 微信 / Google 第三方登录

---

## 开源许可

本项目仅用于学习和交流目的。

---

*Made with ❤️ by 游伴AI Team*
