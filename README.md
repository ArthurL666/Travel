# 🌍 游伴AI — 智能旅行规划助手

> **AI 驱动的个性化旅行规划系统**  
> 基于 Spring AI + Vue 3 构建，通过自然语言交互为你定制专属旅行计划

---

## 📖 目录

- [项目概述](#项目概述)
- [功能特性](#功能特性)
- [技术架构](#技术架构)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [前端界面](#前端界面)
- [AI 对话与工具调用](#ai-对话与工具调用)
- [API 接口文档](#api-接口文档)
- [数据模型](#数据模型)
- [配置说明](#配置说明)
- [常见问题](#常见问题)

---

## 项目概述

**游伴AI**（Travel Buddy AI）是一个基于大语言模型的智能旅行规划系统。用户可以通过自然语言对话的方式，让 AI 助手完成从目的地推荐、景点查询、酒店推荐到完整行程生成的全流程旅行规划。

核心亮点：

- **🤖 AI 驱动**：接入 DeepSeek 大模型（兼容 OpenAI 协议），利用 Function Calling 技术自动调用旅游工具
- **💬 流式对话**：SSE（Server-Sent Events）实时流式响应，逐字展示 AI 思考过程
- **🗺️ 完整行程生成**：AI 根据目的地、天数、偏好自动生成包含每日活动安排的详细行程
- **📱 精美前端**：Vue 3 SPA 应用，支持沉浸式视频背景、毛玻璃效果、3D 卡片动效
- **🔐 用户系统**：基于 JWT 的身份认证、用户资料管理和旅行偏好设置
- **💾 行程管理**：一键保存 AI 生成的行程，支持按天查看详情

---

## 功能特性

### 1. 🏠 首页 — 探索世界
- 全屏视频背景带来沉浸式视觉体验
- 搜索框支持快速输入旅行需求
- 一键跳转至 AI 规划、发现目的地、我的行程等功能模块
- 显示 AI 驱动标识（Powered by DeepSeek）

### 2. 💬 AI 智能规划 — 核心功能
- **多轮对话**：与 AI 旅行助手自然交流，支持上下文记忆
- **工具调用**：AI 自动判断用户意图，调用以下工具：
  - 🏛️ **景点查询**：查询指定城市的景点列表
  - 🏨 **酒店推荐**：按预算档次推荐酒店
  - 🌤️ **天气查询**：获取城市模拟天气数据
  - 📋 **行程生成**：根据天数、目的地、偏好生成完整行程
- **Markdown 渲染**：AI 回复实时渲染成格式化内容
- **历史记录**：查看、加载历史对话记录
- **行程保存**：一键将 AI 生成的行程保存到「我的行程」

### 3. 🗺️ 发现目的地
- 10 大分类筛选（国内、国外、自然风光、城市漫游、海滨度假、美食之旅、文化历史等）
- 16 个精美目的地展示卡片，支持 3D 鼠标追踪动效
- 搜索与热门标签快速筛选
- 个性化推荐模块，根据旅行偏好智能匹配
- 特色推荐横幅（海岛奇遇等主题）
- 空状态设计（无匹配结果时的引导提示）

### 4. ✨ 旅行灵感
- 灵感分类导航（海岛度假、美食之旅、摄影之旅、治愈之旅）
- 热门文章排行榜
- 精选专题（春日赏花、秋日红叶、冬日雪境）
- 瀑布流文章卡片布局
- 用户评价轮播（自动播放）
- AI 偏好推荐：选择你的兴趣标签，AI 生成个性化推荐

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
- 统计面板（行程数量、出行风格、注册时间、预算偏好）

### 8. 🔐 用户认证
- 注册 / 登录（JWT Token 认证）
- Token 自动管理（localStorage 持久化 + Axios 拦截器自动附加）
- 路由守卫（未登录自动跳转登录页）
- 401 自动重定向

---

## 技术架构

```
┌─────────────────────────────────────────────────────────┐
│                    前端 (Vue 3 SPA)                       │
│  Vite 5 + Vue Router 4 + Pinia + Axios + marked         │
│  ┌─────────┬──────────┬─────────┬──────────────┐       │
│  │ Home    │ Chat     │ Explore │ Inspiration   │       │
│  │ Login   │ Plans    │ History │ Profile       │       │
│  └────┬────┴────┬─────┴────┬────┴──────┬───────┘       │
│       │         │          │           │                │
│  ┌────┴─────────┴──────────┴───────────┴───────┐       │
│  │          API Layer (Axios + Interceptor)     │       │
│  └─────────────────────┬────────────────────────┘       │
└────────────────────────┼────────────────────────────────┘
                         │ HTTP / SSE
┌────────────────────────┼────────────────────────────────┐
│             后端 (Spring Boot 3.2.0)                      │
│  ┌──────────────────────┼────────────────────────┐       │
│  │         Controller Layer                         │       │
│  │  AuthController  ChatController  ProfileController│       │
│  └──────────┬───────────┬──────────────┬───────────┘       │
│  ┌──────────┴───────────┴──────────────┴───────────┐       │
│  │         Service Layer                             │       │
│  │  AgentService (AI 对话编排)  UserService (用户)  │       │
│  └──────────┬───────────────────────────┬───────────┘       │
│  ┌──────────┴──────────┐  ┌────────────┴───────────┐       │
│  │  Tool Layer         │  │  Persistence Layer      │       │
│  │  ToolRegistry       │  │  User/Plan/Message Repo │       │
│  │  TravelDataService  │  │  H2 Database            │       │
│  └──────────┬──────────┘  └────────────────────────┘       │
│  ┌──────────┴──────────┐                                    │
│  │  AI Layer           │                                    │
│  │  Spring AI ChatClient│                                    │
│  │  DeepSeek LLM       │                                    │
│  │  Function Calling   │                                    │
│  └─────────────────────┘                                    │
└─────────────────────────────────────────────────────────────┘
```

### 后端技术栈

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 3.2.0 |
| AI 框架 | Spring AI | 1.0.0-M5 |
| 大模型 | DeepSeek (deepseek-v4-flash) | 通过 OpenAI 兼容协议调用 |
| 数据库 | H2 (内存数据库) | — |
| ORM | Spring Data JPA / Hibernate | — |
| 安全 | Spring Security + JWT (jjwt 0.11.5) | — |
| 构建 | Maven | — |
| Java | OpenJDK | 17+ |

### 前端技术栈

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Vue 3 (Composition API) | — |
| 构建 | Vite | 5.x |
| 路由 | Vue Router 4 | Hash 模式 |
| 状态管理 | Pinia | — |
| HTTP | Axios | — |
| Markdown 渲染 | marked | — |
| 字体 | Inter, Noto Sans SC, Schibsted Grotesk, Fustat | Google Fonts |

---

## 快速开始

### 环境要求

- **Java 17+** (OpenJDK)
- **Node.js 18+** (仅前端开发/构建时需要)
- **Maven 3.6+**
- **API Key**：需要 DeepSeek（或其他兼容 OpenAI 协议的 LLM）的 API Key

### 🚀 启动后端

```bash
# 1. 进入项目根目录
cd Travel

# 2. 配置 API Key（设置环境变量）
# Windows PowerShell:
$env:ANTHROPIC_AUTH_TOKEN="your-api-key-here"
# macOS / Linux:
export ANTHROPIC_AUTH_TOKEN="your-api-key-here"

# 3. 使用 Maven 编译并启动
mvn clean install -DskipTests
mvn spring-boot:run
```

后端默认启动在 `http://localhost:8080`。

> **注意**：Maven 的 `pom.xml` 中配置了 `sourceDirectory` 为 `after/src/main/java`，资源目录为 `after/src/main/resources`，请在项目根目录执行 Maven 命令。

### 🚀 启动前端（开发模式）

```bash
# 1. 进入前端目录
cd frontend

# 2. 安装依赖
npm install

# 3. 启动开发服务器
npm run dev
```

前端默认启动在 `http://localhost:3000`，会自动代理 API 请求到后端 `8080` 端口。

### 📦 构建前端（生产模式）

```bash
cd frontend
npm run build
```

构建产物输出到 `frontend/dist/` 目录。

### 访问应用

打开浏览器访问 `http://localhost:3000` 即可使用。

---

## 项目结构

```
Travel/
├── pom.xml                              # Maven 构建文件
├── .gitignore
├── README.md                            # 本说明书
│
├── after/                               # Spring Boot 后端
│   └── src/main/
│       ├── java/com/study/travel/
│       │   ├── TravelApplication.java   # 应用入口
│       │   ├── config/
│       │   │   ├── AIConfig.java        # Spring AI ChatClient 配置 + System Prompt
│       │   │   ├── SecurityConfig.java  # JWT 安全配置
│       │   │   └── WebConfig.java       # CORS 跨域配置
│       │   ├── controller/
│       │   │   ├── AuthController.java  # 认证接口（注册/登录）
│       │   │   ├── ChatController.java  # 聊天接口（流式/同步/历史/行程）
│       │   │   └── ProfileController.java # 用户信息接口
│       │   ├── dto/                     # 数据传输对象
│       │   │   ├── ApiResult.java       # 统一 API 响应包装
│       │   │   ├── AuthResponse.java    # 认证响应
│       │   │   ├── ChatRequest.java     # 聊天请求
│       │   │   ├── ChatResponse.java    # 聊天响应
│       │   │   ├── LoginRequest.java    # 登录请求
│       │   │   ├── PlanSaveRequest.java # 行程保存请求
│       │   │   ├── ProfileUpdateRequest.java # 资料更新请求
│       │   │   └── RegisterRequest.java # 注册请求
│       │   ├── entity/                  # JPA 实体
│       │   │   ├── ChatMessage.java     # 聊天消息实体
│       │   │   ├── TravelPlan.java      # 旅行计划实体
│       │   │   └── User.java            # 用户实体
│       │   ├── repository/              # 数据访问层
│       │   │   ├── ChatMessageRepository.java
│       │   │   ├── TravelPlanRepository.java
│       │   │   └── UserRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java   # JWT 认证过滤器
│       │   │   └── JwtUtil.java         # JWT 工具类
│       │   ├── service/
│       │   │   ├── AgentService.java    # AI 对话编排服务
│       │   │   └── UserService.java     # 用户服务
│       │   └── tool/
│       │       ├── ToolRegistry.java    # AI 工具注册中心
│       │       └── TravelDataService.java # 模拟旅游数据源
│       └── resources/
│           └── application.properties   # 应用配置
│
└── frontend/                            # Vue 3 前端
    ├── index.html                       # HTML 入口
    ├── package.json                     # 依赖清单
    ├── vite.config.js                   # Vite 配置（含 API 代理）
    ├── dist/                            # 构建产物
    └── src/
        ├── main.js                      # Vue 应用入口
        ├── App.vue                      # 根组件
        ├── api/index.js                 # Axios 实例 + 拦截器
        ├── stores/auth.js               # Pinia 认证状态管理
        ├── router/index.js              # 路由配置
        ├── components/AppLayout.vue     # 应用布局组件
        ├── composables/
        │   ├── useChat.js               # 聊天交互逻辑（SSE 流式接收）
        │   └── useToast.js              # Toast 通知
        ├── styles/main.css              # 全局样式 + CSS 变量
        ├── assets/
        │   ├── logo-full.svg            # 完整 Logo
        │   └── logo-icon.svg            # Logo 图标
        └── views/
            ├── HomeView.vue             # 🏠 首页
            ├── LoginView.vue            # 🔐 登录/注册
            ├── ChatView.vue             # 💬 AI 对话
            ├── ExploreView.vue          # 🗺️ 发现目的地
            ├── InspirationView.vue      # ✨ 旅行灵感
            ├── PlansView.vue            # 📋 我的行程
            ├── HistoryView.vue          # 📋 历史对话
            └── ProfileView.vue          # 👤 个人中心
```

---

## 前端界面

### 路由结构

| 路径 | 页面 | 说明 |
|------|------|------|
| `/` | HomeView | 首页，视频背景 + 搜索入口 |
| `/login` | LoginView | 登录/注册切换，玻璃拟态卡片 |
| `/app/chat` | ChatView | AI 智能规划（核心页面） |
| `/app/explore` | ExploreView | 发现目的地 |
| `/app/inspiration` | InspirationView | 旅行灵感 |
| `/app/plans` | PlansView | 我的行程 |
| `/app/history` | HistoryView | 历史对话记录 |
| `/app/profile` | ProfileView | 个人中心 |

### 路由守卫

- 所有 `/app/*` 路径需要登录认证，未登录自动跳转 `/login`
- 已登录用户访问 `/login` 自动跳转回首页

### 聊天交互流程

```
用户在输入框输入消息
        │
        ▼
POST /api/chat/stream (SSE)
        │
        ▼
AI 分析用户意图
        │
        ├── 询问目的地 → 推荐城市
        ├── 问天气     → 调用 getWeather 工具
        ├── 问景点     → 调用 getScenicSpots 工具
        ├── 问酒店     → 调用 getHotels 工具
        └── 要行程 → 调用 generateTravelPlan 工具
                │
                ▼
AI 整合工具结果，生成格式化回复
        │
        ▼
逐字符 SSE 流式推送到前端
        │
        ▼
marked 库渲染 Markdown 为 HTML
        │
        ▼
用户点击「💾 保存行程」→ 保存到「我的行程」
```

### 前端设计风格

- **配色**：天空蓝主色调 (`#0EA5E9`)，渐变强调色
- **效果**：毛玻璃 (backdrop-filter: blur)、平滑过渡动画
- **卡片**：圆角卡片、悬浮抬升、3D 鼠标追踪动效
- **骨架屏**：所有页面在数据加载时显示骨架屏占位
- **空状态**：每个列表页面都有友好的空状态引导
- **响应式**：支持桌面端和移动端适配

---

## AI 对话与工具调用

### System Prompt

AI 旅行助手的系统提示词定义在 `AIConfig.java` 中，核心指令：

> 你是专业的 AI 旅游规划助手「旅行小助手」
> - 用户提到目的地+天数 → 调用 `generateTravelPlan`
> - 用户问天气 → 调用 `getWeather`
> - 用户问景点 → 调用 `getScenicSpots`
> - 用户问酒店 → 调用 `getHotels`
> - 回复中使用适当 emoji 和 Markdown 格式
> - 如果用户没有明确目的地，主动推荐热门城市

### 注册的 AI 工具

| 工具名称 | 功能描述 | 输入参数 | 
|---------|---------|---------|
| `getScenicSpots` | 查询城市景点列表 | `{ city: string }` |
| `getHotels` | 按预算推荐酒店 | `{ city: string, budget?: string }` |
| `getWeather` | 查询模拟天气 | `{ city: string }` |
| `generateTravelPlan` | 生成完整行程规划 | `{ city: string, days: number, preferences?: string }` |

### 旅游数据覆盖城市

目前内置 10 个城市的模拟数据（每个城市 6 个景点 + 3 家酒店）：

| 城市 | 景点举例 | 酒店举例 |
|------|---------|---------|
| 🏯 北京 | 故宫、长城、颐和园 | 希尔顿、四合院民宿、如家 |
| 🌃 上海 | 外滩、东方明珠、迪士尼 | 和平饭店、文华东方、全季 |
| 🌊 杭州 | 西湖、灵隐寺、龙井村 | 西湖国宾馆、法云安缦、汉庭 |
| 🐼 成都 | 大熊猫基地、宽窄巷子、都江堰 | 博舍、成都院子、锦江之星 |
| 🏖️ 三亚 | 亚龙湾、蜈支洲岛、天涯海角 | 亚特兰蒂斯、艾迪逊、海景公寓 |
| 🏛️ 西安 | 兵马俑、大雁塔、不夜城 | 索菲特传奇、W 酒店、汉庭 |
| 🗼 东京 | 浅草寺、东京塔、秋叶原 | 半岛酒店、格兰贝尔、民宿 |
| 🥐 巴黎 | 埃菲尔铁塔、卢浮宫、凡尔赛宫 | 丽兹酒店、铂尔曼、宜必思 |
| 🏘️ 丽江 | 丽江古城、玉龙雪山、泸沽湖 | 悦榕庄、英迪格、花间堂 |
| 🌊 厦门 | 鼓浪屿、厦大、环岛路 | 康莱德、鼓浪屿民宿、如家 |

---

## API 接口文档

所有接口统一前缀 `/api`，认证接口无需 Token，其余需要 `Authorization: Bearer <token>` 请求头。

### 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

### 🔐 认证接口

#### `POST /api/auth/register` 注册

```json
{ "username": "string", "password": "string" }
```

#### `POST /api/auth/login` 登录

```json
{ "username": "string", "password": "string" }
```

**响应**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "jwt-token-string",
    "username": "user",
    "nickname": "用户昵称"
  }
}
```

### 💬 聊天接口 (需认证)

#### `POST /api/chat/stream` 流式对话

- 请求体：`{ "message": "用户消息" }`
- 响应：`text/event-stream` (SSE)，`Flux<String>` 流式输出

#### `POST /api/chat` 同步对话

- 请求体：`{ "message": "用户消息" }`
- 响应：完整的 AI 回复内容

#### `GET /api/chat/history` 获取历史

返回当前用户的所有聊天记录（按时间升序）。

#### `DELETE /api/chat/history/{id}` 删除单条

#### `DELETE /api/chat/history` 清空所有

### 📋 行程接口 (需认证)

#### `POST /api/chat/plan/save` 保存行程

```json
{ "city": "北京", "days": 5, "content": "行程Markdown内容", "preferences": "" }
```

#### `GET /api/chat/plan/list` 获取行程列表

#### `DELETE /api/chat/plan/{id}` 删除行程

### 👤 用户接口 (需认证)

#### `GET /api/user/profile` 获取用户信息

#### `PUT /api/user/profile` 更新资料

```json
{ "nickname": "新昵称", "preferences": { "style": "relax", "budget": "medium" } }
```

---

## 数据模型

### User (用户)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| username | String (unique) | 用户名 |
| password | String | BCrypt 加密密码 |
| nickname | String (nullable) | 昵称 |
| avatar | String (nullable) | 头像 URL |
| createTime | LocalDateTime | 注册时间 |

### TravelPlan (旅行计划)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| userId | Long | 用户 ID |
| city | String | 目的地城市 |
| days | Integer | 旅行天数 |
| content | Text | AI 生成的行程内容 (Markdown) |
| preferences | String (nullable) | 用户偏好 |
| createTime | LocalDateTime | 创建时间 |

### ChatMessage (聊天消息)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 主键，自增 |
| userId | Long | 用户 ID |
| role | String | "user" 或 "assistant" |
| content | Text | 消息内容 |
| createTime | LocalDateTime | 发送时间 |

---

## 配置说明

### application.properties 关键配置

```properties
# 服务器端口
server.port=8080

# 数据库（H2 内存数据库）
spring.datasource.url=jdbc:h2:mem:travel_agent
spring.jpa.hibernate.ddl-auto=update

# JWT 密钥（生产环境请更换）
app.jwt.secret=TravelAssistant2024SecretKeyForJWTTokenGenerationAndValidation
app.jwt.expiration=86400000    # Token 有效期 24 小时

# LLM 配置（OpenAI 兼容协议）
spring.ai.openai.base-url=https://api.deepseek.com
spring.ai.openai.api-key=${ANTHROPIC_AUTH_TOKEN}
spring.ai.openai.chat.options.model=deepseek-v4-flash
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.chat.options.max-tokens=2000
```

### 更换 LLM 提供商

如需更换其他兼容 OpenAI 协议的模型（如通义千问、GPT 等），只需修改：

```properties
spring.ai.openai.base-url=https://your-llm-provider-api-url
spring.ai.openai.api-key=your-api-key
spring.ai.openai.chat.options.model=your-model-name
```

例如使用通义千问：
```properties
spring.ai.openai.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
spring.ai.openai.api-key=your-dashscope-api-key
spring.ai.openai.chat.options.model=qwen-plus
```

### 前端 API 代理配置

`frontend/vite.config.js` 中配置了代理，将 `/api` 请求转发到后端：

```js
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true
  }
}
```

---

## 常见问题

### Q: 启动后端报错 "API Key not configured"？
A: 请确保已设置环境变量 `ANTHROPIC_AUTH_TOKEN`，或在 `application.properties` 中直接填入你的 API Key。

### Q: 是否必须联网？
A: 是的，AI 对话功能需要调用 DeepSeek API，必须联网。但首页浏览、目的地发现等静态页面可离线使用。

### Q: 数据会丢失吗？
A: 当前使用 H2 内存数据库，应用重启后数据会丢失。如需持久化，可替换为 MySQL/PostgreSQL：
1. 添加对应数据库驱动依赖
2. 修改 `spring.datasource.url` 等配置
3. 将 `ddl-auto` 设为 `update` 或使用 Flyway/Liquibase 管理迁移

### Q: 如何替换为真实 API 数据？
A: `TravelDataService.java` 中的景点、酒店、天气数据为模拟数据。如需接入真实 API：
1. 在 `TravelDataService` 中调用第三方旅游 API（如携程、高德、OpenWeather）
2. 或在 `ToolRegistry` 的工具回调中直接调用外部服务

### Q: 如何增加新的支持城市？
A: 在 `TravelDataService.java` 的静态代码块中，按现有格式添加新城市景点和酒店数据即可。前端目的地页面也需要在 `ExploreView.vue` 的 `destinations` 数组中添加对应条目。

### Q: 前端构建报错？
A: 确保 Node.js 版本 ≥ 18，并运行 `npm install` 安装所有依赖。如有缓存问题，可删除 `node_modules` 和 `package-lock.json` 后重新安装。

### Q: 如何关闭 H2 控制台？
A: 删除或注释 `application.properties` 中的 `spring.h2.console.enabled=true`。

---

## 开发计划 / 可能的扩展方向

- [ ] **数据库持久化**：替换 H2 为 MySQL/PostgreSQL
- [ ] **真实旅游 API**：接入高德地图、携程、OpenWeather 等真实数据源
- [ ] **Redis 对话缓存**：用 Redis 替代内存 ChatMemory，支持分布式
- [ ] **文件上传**：支持用户上传头像
- [ ] **社交功能**：行程分享、用户评论、旅行游记发布
- [ ] **移动端适配**：PWA 或原生 App 封装
- [ ] **多语言支持**：国际化 i18n
- [ ] **行程导出**：PDF / 图片格式导出行程单
- [ ] **OAuth 登录**：微信 / Google 第三方登录
- [ ] **消息通知**：天气变化提醒、出行前提醒

---

## 开源许可

本项目仅用于学习和交流目的。

---

*Made with ❤️ by 游伴AI Team*
