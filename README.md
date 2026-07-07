# 🌍 游伴 AI — 智能旅行规划助手

> **AI 驱动的个性化旅行规划系统** — 通过自然语言对话定制专属旅行计划
>
> 基于 Spring Boot 3 + Vue 3 全栈架构，集成 DeepSeek 大语言模型，具备工具调用（Function Calling）、流式对话、用户认证、行程管理等完整能力。

---

## 📋 目录

- [项目概述](#项目概述)
- [功能总览](#功能总览)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [页面路由](#页面路由)
- [AI 架构](#ai-架构)
- [API 参考](#api-参考)
- [数据模型](#数据模型)
- [配置指南](#配置指南)
- [常见问题](#常见问题)
- [扩展方向](#扩展方向)

---

## 项目概述

**游伴 AI**（Travel Buddy AI）是一个基于大语言模型的智能旅行规划系统。用户通过自然语言与 AI 旅行助手对话，即可完成从 **目的地推荐 → 景点查询 → 酒店推荐 → 天气查询 → 完整行程生成** 的全流程旅行规划。

### 核心特性

| 特性 | 说明 |
|------|------|
| 🤖 **AI 驱动规划** | 接入 DeepSeek 大模型，通过 Function Calling 自动调用工具，智能生成行程 |
| 💬 **流式实时对话** | SSE 流式响应 + 打字机逐字输出效果 + 思考阶段动画 |
| 🛠️ **四类工具调用** | 景点查询、酒店推荐、天气查询、行程生成，AI 自动判断用户意图并调用 |
| 🔐 **用户系统** | JWT 身份认证 + 注册登录 + 个人资料管理 |
| 💾 **行程管理** | 一键保存 AI 生成的行程，按天查看详情 |
| 🎨 **精美前端** | 毛玻璃效果、3D 卡片动效、沉浸式视觉体验 |
| 🗺️ **发现目的地** | 16 个目的地卡片，10 类筛选，个性化推荐 |

---

## 功能总览

### 1. 🏠 首页 — 探索世界

- **视频背景** — 自动播放的全屏旅行视频，营造沉浸式氛围
- **导航入口** — AI 规划、发现目的地、我的行程、旅行灵感快捷跳转
- **搜索功能** — 输入目的地关键词，快速查找相关页面
- **推荐卡片** — 显示热门目的地和推荐语
- **底部导航** — AI 规划、发现目的地、我的行程、个人中心

### 2. 💬 AI 智能规划 — 核心功能

| 能力 | 描述 |
|------|------|
| **多轮对话** | 与 AI 旅行助手自然交流，完整上下文记忆 |
| **工具调用** | AI 自动判断意图 → 调用景点/酒店/天气/行程工具 → 整合回复 |
| **打字机效果** | SSE 流式接收 + 动态速度逐字输出，首批 Token 到达后自动关闭思考动画 |
| **Markdown 渲染** | AI 回复实时渲染为格式化内容（表格、列表、标题等） |
| **历史记录** | 查看和加载历史对话记录（侧边面板） |
| **行程保存** | 一键将 AI 生成的行程保存至「我的行程」 |
| **快捷提示** | 预置热门问法，一键发送 |

### 3. 🗺️ 发现目的地

- **10 大分类筛选** — 全部、国内、国外、自然风光、城市漫游、海滨度假、美食之旅、文化历史、当季推荐、热门精选
- **16 个精美目的地卡片** — 支持 3D 鼠标追踪动效
- **搜索与热门标签** — 快速筛选
- **猜你喜欢** — 按偏好标签推荐目的地（轻户外/美食家/摄影控/文化迷/奢华游）
- **目的地详情页** — 轮播画廊、深度介绍、景点清单、酒店推荐、实用贴士

### 4. ✨ 旅行灵感

- **灵感分类导航** — 海岛度假、美食之旅、摄影之旅、治愈之旅
- **热门文章排行榜** — 发现最受欢迎的旅行内容
- **精选专题合集** — 深度主题内容聚合
- **瀑布流文章卡片** — 流畅的浏览体验
- **用户评价轮播** — 真实用户分享（自动播放）
- **AI 偏好推荐** — 选择标签后 AI 生成个性化推荐
- **文章/合集详情页** — 深度阅读体验，图文混排

### 5. 📋 我的行程

- **行程列表** — 每条显示目的地、旅行天数和创建时间
- **渐变色封面 + 城市图标** — 视觉化的行程卡片
- **按天查看详情** — Day 选项卡切换，查看每日活动安排
- **删除管理** — 支持删除行程

### 6. 💬 历史对话

- **对话记录** — 查看历史 AI 对话内容
- **清空操作** — 一键清空所有记录

### 7. 👤 个人中心

- **用户信息管理** — 昵称编辑
- **统计面板** — 行程数量、出行风格、注册时间、预算偏好展示
- **旅行偏好设置** — 出行风格、预算范围、偏好季节、同行人员
- **账户信息** — 用户名、注册时间、账户状态

### 8. 🔐 用户认证

- **注册/登录** — JWT Token 认证，密码 BCrypt 加密
- **Token 自动管理** — localStorage 持久化 + Axios 拦截器自动附加
- **路由守卫** — 未登录自动跳转登录页

---

## 技术栈

### 后端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 3.2.0 |
| AI 框架 | Spring AI | 1.0.0-M5 |
| 大语言模型 | DeepSeek | `deepseek-v4-flash`（OpenAI 兼容协议） |
| 数据库 | H2（内存数据库） | — |
| ORM | Spring Data JPA / Hibernate | — |
| 安全认证 | Spring Security + JWT | jjwt 0.11.5 |
| 构建工具 | Maven | 3.6+ |
| 运行环境 | OpenJDK | 17+ |

### 前端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Vue 3（Composition API + `<script setup>`） | ^3.4.0 |
| 构建工具 | Vite | 5.x |
| 路由 | Vue Router 4（Hash 模式） | ^4.2.0 |
| 状态管理 | Pinia | ^2.1.0 |
| HTTP 客户端 | Axios | ^1.6.0 |
| Markdown 渲染 | marked | ^9.1.0 |

---

## 快速开始

### 环境要求

| 依赖 | 版本要求 | 用途 |
|------|---------|------|
| **Java** | 17+ (OpenJDK) | 运行后端服务 |
| **Maven** | 3.6+ | 后端构建 |
| **Node.js** | 18+ | 前端构建与开发 |
| **npm** | 9+ | 前端依赖管理 |

### 🚀 启动后端

```bash
# 1. 进入项目根目录
cd Travel

# 2. 编译
mvn clean install -DskipTests

# 3. 启动
mvn spring-boot:run
```

后端默认启动在 **`http://localhost:8081`**。

> API Key 已配置在 `application.properties` 中，默认使用 DeepSeek 接口。如需更换 Key，直接编辑该文件中的 `spring.ai.openai.api-key` 字段。

### 🚀 启动前端（开发模式）

```bash
# 1. 进入前端目录
cd frontend

# 2. 安装依赖
npm install

# 3. 启动开发服务器
npm run dev
```

前端默认启动在 **`http://localhost:3000`**，开发模式下自动将 `/api` 请求代理到后端 `8081` 端口。

### 📦 构建前端（生产模式）

```bash
cd frontend
npm run build
```

构建产物输出至 `frontend/dist/` 目录。

### 访问应用

打开浏览器访问 **`http://localhost:3000`**，注册账号后即可开始使用。

---

## 项目结构

```
Travel/
├── pom.xml                            # Maven 构建文件
├── README.md                          # 项目文档
├── .gitignore
│
├── after/                             # ====== Spring Boot 后端 ======
│   └── src/main/
│       ├── java/com/study/travel/
│       │   ├── TravelApplication.java         # @SpringBootApplication 入口
│       │   ├── config/
│       │   │   ├── AIConfig.java              # ChatClient + SystemPrompt + ChatMemory
│       │   │   ├── SecurityConfig.java        # JWT + Spring Security 过滤链
│       │   │   └── WebConfig.java             # CORS 跨域配置
│       │   ├── controller/
│       │   │   ├── AuthController.java        # 注册/登录 API
│       │   │   ├── ChatController.java        # SSE 流式对话 / 历史 / 行程 CRUD
│       │   │   └── ProfileController.java     # 用户资料 API
│       │   ├── dto/                           # 数据传输对象
│       │   │   ├── ApiResult.java             # 统一响应 {code, message, data}
│       │   │   ├── AuthResponse.java          # {token, username, nickname}
│       │   │   ├── ChatRequest.java           # {message}
│       │   │   ├── ChatResponse.java          # {role, content, timestamp}
│       │   │   ├── LoginRequest.java          # {username, password}
│       │   │   ├── RegisterRequest.java       # {username, password, nickname?}
│       │   │   ├── PlanSaveRequest.java       # {city, days, content, preferences?}
│       │   │   └── ProfileUpdateRequest.java  # {nickname?, avatar?}
│       │   ├── entity/                        # JPA 实体
│       │   │   ├── User.java                  # → users 表
│       │   │   ├── ChatMessage.java           # → chat_messages 表
│       │   │   └── TravelPlan.java            # → travel_plans 表
│       │   ├── repository/                    # 数据访问层
│       │   │   ├── UserRepository.java
│       │   │   ├── ChatMessageRepository.java
│       │   │   └── TravelPlanRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java         # Token 提取 & 校验过滤器
│       │   │   └── JwtUtil.java               # HS256 JWT 生成 & 验证
│       │   ├── service/
│       │   │   ├── AgentService.java          # ChatClient 封装（流式/同步对话）
│       │   │   └── UserService.java           # 注册/登录/资料管理
│       │   └── tool/
│       │       ├── ToolRegistry.java          # 4 个 FunctionCallback 注册
│       │       └── TravelDataService.java     # 10 城市模拟数据
│       └── resources/
│           └── application.properties         # 服务端配置
│
└── frontend/                           # ====== Vue 3 前端 ======
    ├── index.html                     # SPA 入口
    ├── package.json                   # 依赖与脚本
    ├── vite.config.js                 # Vite 配置 + API 代理
    └── src/
        ├── main.js                    # Vue 应用入口
        ├── App.vue                    # 根组件
        ├── api/index.js               # Axios 实例 + JWT 拦截器
        ├── stores/auth.js             # Pinia 认证状态
        ├── router/index.js            # Hash 路由 + 守卫
        ├── components/
        │   ├── AppLayout.vue          # 主布局（底部导航栏）
        │   └── AiIcon.vue             # AI 装饰图标
        ├── composables/
        │   ├── useChat.js             # SSE 流式聊天 + 打字机效果
        │   ├── useToast.js            # Toast 通知
        │   └── usePlanCharts.js       # 图表辅助（预留）
        ├── data/
        │   ├── destinations.js        # 16 目的地卡片数据
        │   ├── articles.js            # 文章数据
        │   └── images.js              # 图片资源引用
        ├── styles/main.css            # 全局样式
        └── views/
            ├── HomeView.vue           # 🏠 首页
            ├── LoginView.vue          # 🔐 登录/注册
            ├── ChatView.vue           # 💬 AI 智能规划（核心页面）
            ├── ExploreView.vue        # 🗺️ 发现目的地
            ├── DestinationDetailView.vue  # 目的地详情页
            ├── InspirationView.vue    # ✨ 旅行灵感
            ├── ArticleDetailView.vue  # 文章详情
            ├── CollectionDetailView.vue  # 专题合集详情
            ├── PlansView.vue          # 📋 我的行程
            └── ProfileView.vue        # 👤 个人中心
```

---

## 页面路由

| 路径 | 页面 | 说明 |
|------|------|------|
| `/` | 首页 | 视频背景 + 搜索 + 导航入口，游客可访问 |
| `/login` | 登录/注册 | 玻璃拟态卡片，登录注册切换 |
| `/app/chat` | AI 智能规划 | ⭐ 核心页面 |
| `/app/explore` | 发现目的地 | 分类筛选 + 3D 卡片 |
| `/app/destination/:name` | 目的地详情 | 轮播画廊 + 深度介绍 |
| `/app/inspiration` | 旅行灵感 | 文章 + 专题 + 推荐 |
| `/app/article/:slug` | 文章详情 | 深度阅读 |
| `/app/collection/:slug` | 合集详情 | 专题内容 |
| `/app/plans` | 我的行程 | 保存的旅行计划 |
| `/app/profile` | 个人中心 | 资料 + 偏好设置 |

> **路由守卫**：`/` 和 `/login` 对所有用户开放；`/app/*` 下的页面需要登录认证，未登录自动跳转 `/login`。

---

## AI 架构

### 对话流程

```
┌─────────────────────┐     POST /api/chat/stream      ┌───────────────────────────┐
│     Vue 3 前端       │ ──── { message } ───────────→  │    Spring Boot 后端       │
│                     │ ←── SSE (text/event-stream) ─── │    ChatController        │
│   useChat()         │       data: 逐字 Token 内容    └───────────┬───────────────┘
│   composable        │                                               │
│   fetch + SSE       │                                               ▼
│   EventSource       │                                   ┌───────────────────────────┐
└─────────────────────┘                                   │    AgentService           │
                                                          │                           │
                                                          │  ChatClient (固定实例)     │
                                                          │  ┌───────────────────┐    │
                                                          │  │  System Prompt    │    │
                                                          │  │  (旅游助手角色)    │    │
                                                          │  ├───────────────────┤    │
                                                          │  │  ChatMemory       │    │
                                                          │  │  (多轮上下文)      │    │
                                                          │  ├───────────────────┤    │
                                                          │  │  Function Calling │    │
                                                          │  │  (4 个工具)       │    │
                                                          │  └───────────────────┘    │
                                                          │           │               │
                                                          │           ▼               │
                                                          │  ┌───────────────────┐    │
                                                          │  │  DeepSeek API     │    │
                                                          │  │  (OpenAI 协议)    │    │
                                                          │  └───────────────────┘    │
                                                          └───────────────────────────┘
```

核心调用链路：
1. 前端通过 `fetch()` 发起 POST 请求到 `/api/chat/stream`
2. `ChatController` 接收消息，调用 `AgentService`
3. `AgentService` 通过 Spring AI 的 `ChatClient.prompt().stream()` 发起流式对话
4. `ChatClient` 集成 System Prompt + ChatMemory（多轮对话）+ Function Calling
5. LLM 根据用户意图自主决策调用工具
6. 结果通过 SSE 流式返回前端，触发打字机逐字显示

### 系统提示词

定义在 `AIConfig.java` 的 `ChatClient` 构建中：

- **角色**：专业 AI 旅游规划助手「旅行小助手」
- **工具触发规则**：
  - 目的地 + 天数 → 调用 `generateTravelPlan`
  - 问天气 → 调用 `getWeather`
  - 问景点 → 调用 `getScenicSpots`
  - 问酒店 → 调用 `getHotels`
- **回复风格**：使用 emoji 和 Markdown 格式增强可读性
- **主动推荐**：用户未明确目的地时推荐热门城市
- **偏好记忆**：在多轮对话中记住用户偏好

### 注册的 AI 工具（Function Calling）

| 工具名称 | 功能 | 输入参数 | 返回内容 |
|---------|------|---------|---------|
| `getScenicSpots` | 查询城市景点 | `city: string` | 名称、简介、票价、评分、开放时间、类别 |
| `getHotels` | 按预算推荐酒店 | `city: string, budget?: string` | 名称、档次、位置、价格、评分、特色 |
| `getWeather` | 查询城市天气 | `city: string` | 天气类型、温度、出行建议 |
| `generateTravelPlan` | 生成完整行程规划 | `city: string, days: int, preferences?: string` | 每日活动安排 + 酒店推荐 + 天气提示 |

### 模拟数据

内置 **10 个城市**的模拟旅游数据：

| 国内 | 北京、上海、杭州、成都、三亚、西安、丽江、厦门 |
|------|-----------------------------------------------|
| 国外 | 东京、巴黎 |

每个城市包含 **6 个景点** 和 **3 家酒店**，涵盖历史文化、园林自然、美食购物、艺术文创、海滨度假、城市景观、主题乐园等多种类别。天气数据为随机生成。

---

## API 参考

### 通用约定

- **基础路径**：所有接口统一前缀 `/api`
- **认证方式**：除 `/api/auth/*` 外，需在请求头携带 `Authorization: Bearer <token>`
- **响应格式**：

```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

### 🔐 认证接口（无需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `POST` | `/api/auth/register` | `{ username, password, nickname? }` | 注册新用户 |
| `POST` | `/api/auth/login` | `{ username, password }` | 登录，返回 JWT Token |

**登录响应示例**：
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "traveler",
    "nickname": "旅行者小明"
  }
}
```

### 💬 聊天接口（需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `POST` | `/api/chat/stream` | `{ message }` | ⭐ 流式对话（SSE），实现打字机效果 |
| `POST` | `/api/chat` | `{ message }` | 同步对话，返回完整 AI 回复 |
| `GET` | `/api/chat/history` | — | 获取当前用户聊天记录 |
| `DELETE` | `/api/chat/history/{id}` | — | 删除单条记录 |
| `DELETE` | `/api/chat/history` | — | 清空所有记录 |

### 📋 行程接口（需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `POST` | `/api/chat/plan/save` | `{ city, days, content, preferences? }` | 保存 AI 生成的行程 |
| `GET` | `/api/chat/plan/list` | — | 获取行程列表 |
| `DELETE` | `/api/chat/plan/{id}` | — | 删除指定行程 |

### 👤 用户接口（需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `GET` | `/api/user/profile` | — | 获取用户信息 |
| `PUT` | `/api/user/profile` | `{ nickname?, avatar?, preferences? }` | 更新资料与偏好 |

---

## 数据模型

### User（用户表 → `users`）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `Long` | `@Id`, `@GeneratedValue` | 主键，自增 |
| `username` | `String` | `@Column(unique = true)`, `@NotBlank` | 用户名（唯一） |
| `password` | `String` | `@NotBlank` | BCrypt 加密密码 |
| `nickname` | `String` | 可空 | 用户昵称 |
| `createTime` | `LocalDateTime` | `@PrePersist` | 注册时间，自动生成 |

### ChatMessage（聊天消息表 → `chat_messages`）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `Long` | `@Id`, `@GeneratedValue` | 主键，自增 |
| `userId` | `Long` | — | 用户 ID |
| `role` | `String` | — | `"user"` 或 `"assistant"` |
| `content` | `String` | `@Lob`, `@Column(columnDefinition = "TEXT")` | 消息内容 |
| `createTime` | `LocalDateTime` | `@PrePersist` | 发送时间，自动生成 |

### TravelPlan（旅行计划表 → `travel_plans`）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `Long` | `@Id`, `@GeneratedValue` | 主键，自增 |
| `userId` | `Long` | — | 用户 ID |
| `city` | `String` | — | 目的地城市 |
| `days` | `Integer` | — | 旅行天数 |
| `content` | `String` | `@Lob`, `@Column(columnDefinition = "TEXT")` | AI 生成的 Markdown 行程 |
| `preferences` | `String` | 可空 | 用户旅行偏好 |
| `createTime` | `LocalDateTime` | `@PrePersist` | 创建时间，自动生成 |

---

## 配置指南

### application.properties

```properties
# 服务端口
server.port=8081

# 数据库（H2 内存模式）
spring.datasource.url=jdbc:h2:mem:travel_agent;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.open-in-view=false

# JWT（生产环境请更换密钥）
app.jwt.secret=TravelAssistant2024SecretKeyForJWTTokenGenerationAndValidation
app.jwt.expiration=86400000

# LLM — OpenAI 兼容协议（默认 DeepSeek）
spring.ai.openai.base-url=https://api.deepseek.com
spring.ai.openai.api-key=sk-your-key-here
spring.ai.openai.chat.options.model=deepseek-v4-flash
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.chat.options.max-tokens=2000
```

### 更换 LLM 提供商

```properties
# 通义千问（阿里云）
spring.ai.openai.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
spring.ai.openai.chat.options.model=qwen-plus

# OpenAI GPT
spring.ai.openai.base-url=https://api.openai.com/v1
spring.ai.openai.chat.options.model=gpt-4o
```

### 前端 API 代理（vite.config.js）

```js
proxy: {
  '/api': {
    target: 'http://localhost:8081',
    changeOrigin: true
  }
}
```

### 数据库持久化（替换 H2 为 MySQL/PostgreSQL）

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/travel_agent?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/travel_agent
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

> 更换数据库后需在 `pom.xml` 中添加对应的 JDBC 驱动依赖。

---

## 常见问题

### Q: 启动后端需要配置 API Key 吗？

**A:** 需要。`application.properties` 中的 `spring.ai.openai.api-key` 需要替换为你自己的 DeepSeek API Key。可在 [platform.deepseek.com](https://platform.deepseek.com/api_keys) 获取。

### Q: 更换 API Key 后需要重新编译吗？

**A:** 不需要。修改 `application.properties` 后重启后端服务即可生效。

### Q: 是否必须联网才能使用？

**A:** AI 对话需调用 DeepSeek API，**必须联网**。首页浏览、发现目的地、旅行灵感等静态页面可在离线状态下查看。

### Q: 数据会丢失吗？

**A:** 当前使用 H2 **内存数据库**，服务重启后所有数据会丢失。如需持久化，请替换为 MySQL 或 PostgreSQL（参见配置指南）。

### Q: 如何替换为真实 API 数据？

**A:** `TravelDataService.java` 中的景点、酒店、天气数据均为模拟数据。可在该类中调用第三方 API（如高德地图 POI、携程开放平台、OpenWeatherMap）替代静态模拟数据。

### Q: 如何增加新的支持城市？

**A:** 需要修改两处：
- **后端**：在 `TravelDataService.java` 的静态代码块中按现有格式添加景点和酒店数据
- **前端**：在 `frontend/src/data/destinations.js` 中添加对应城市的展示条目

---

## 扩展方向

- [ ] **数据库持久化** — 替换 H2 为 MySQL/PostgreSQL，避免重启数据丢失
- [ ] **真实旅游 API** — 接入高德地图、携程、OpenWeather 等第三方数据源
- [ ] **Redis 对话缓存** — 替代内存 ChatMemory，支持分布式部署
- [ ] **用户自定义 API Key** — 支持用户在前端自行配置 API Key（增强灵活性）
- [ ] **文件上传** — 支持用户上传自定义头像
- [ ] **社交功能** — 行程分享、用户评论、游记发布
- [ ] **多语言 i18n** — 中英文国际化支持
- [ ] **行程导出** — PDF / 图片格式导出
- [ ] **OAuth 登录** — 微信 / Google 第三方登录

---

<div align="center">
  <sub>Made with ❤️ by 游伴 AI Team</sub>
</div>
