# 🌍 游伴 AI — 智能旅行规划助手

> **AI 驱动的个性化旅行规划系统 — 通过自然语言对话，定制专属旅行计划**
>
> 基于 **Spring AI + Vue 3** 全栈架构，集成 **DeepSeek 大语言模型**，具备工具调用（Function Calling）、流式对话、用户认证、行程管理等完整能力。

<div align="center">

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.4-4FC08D?logo=vue.js)](https://vuejs.org/)
[![DeepSeek](https://img.shields.io/badge/LLM-DeepSeek-4F46E5?logo=openai)](https://platform.deepseek.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow)](#开源许可)

</div>

---

## 📋 目录

- [项目概述](#项目概述)
- [功能总览](#功能总览)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [项目结构](#项目结构)
- [页面路由](#页面路由)
- [AI 架构](#ai-架构)
- [用户自定义 API Key](#用户自定义-api-key)
- [API 参考](#api-参考)
- [数据模型](#数据模型)
- [配置指南](#配置指南)
- [常见问题](#常见问题)
- [扩展方向](#扩展方向)
- [开源许可](#开源许可)

---

## 项目概述

**游伴 AI**（Travel Buddy AI）是一个基于大语言模型的智能旅行规划系统。用户通过自然语言与 AI 旅行助手对话，即可完成从 **目的地推荐 → 景点查询 → 酒店推荐 → 天气查询 → 完整行程生成** 的全流程旅行规划。

### 核心特性

| 特性 | 说明 |
|------|------|
| 🤖 **AI 驱动规划** | 接入 DeepSeek 大模型，通过 Function Calling 自动调用旅游工具，智能生成行程 |
| 💬 **流式实时对话** | SSE (Server-Sent Events) 实时流式响应，打字机逐字输出效果，思考阶段动效 |
| 🛠️ **四类工具调用** | 景点查询、酒店推荐、天气查询、行程生成，AI 自动判断用户意图并调用 |
| 🔑 **用户自配 API Key** | 无需服务端配置，用户在前端即可填入自己的 DeepSeek API Key |
| 🎨 **沉浸式前端体验** | 毛玻璃效果、3D 卡片动效、轮播画廊、瀑布流布局、动态图表 |
| 🔐 **完整用户系统** | JWT 身份认证、用户资料管理、旅行偏好设置 |
| 💾 **行程管理** | 一键保存 AI 生成的行程，按天查看详情，随时回顾管理 |
| 📱 **响应式设计** | 适配桌面与移动端，流畅的交互与转场动画 |

---

## 功能总览

### 1. 🏠 首页 — 探索世界起点

- **智能搜索框** — 快速输入旅行需求，一键跳转至 AI 规划
- **快捷入口** — AI 规划、发现目的地、我的行程等功能模块快速访问
- **AI 驱动标识** — 展示 AI 能力，降低使用门槛

### 2. 💬 AI 智能规划 — 核心功能

| 能力 | 描述 |
|------|------|
| **多轮对话** | 与 AI 旅行助手自然交流，完整上下文记忆，持续优化行程 |
| **工具调用** | AI 自动判断意图 → 调用景点/酒店/天气/行程工具 → 整合回复 |
| **打字机效果** | SSE 流式接收 + 动态速度逐字输出，首批 Token 到达后自动关闭思考动画 |
| **Markdown 渲染** | AI 回复实时渲染为格式化内容（表格、列表、代码块） |
| **历史记录** | 查看和加载历史对话记录，继续中断的规划 |
| **行程保存** | 一键将 AI 生成的行程保存至「我的行程」，支持后续查看 |
| **API Key 自助配置** | 前端弹窗输入 API Key，仅存本地浏览器，安全可控 |

### 3. 🗺️ 发现目的地

- **10 大分类筛选** — 国内、国外、自然风光、城市漫游、海滨度假、美食之旅、文化历史等
- **16 个精美目的地卡片** — 支持 3D 鼠标追踪动效
- **搜索与热门标签** — 快速筛选目标城市
- **个性化推荐** — 根据旅行偏好智能匹配目的地
- **目的地详情页** — 沉浸式轮播画廊、深度介绍、景点清单、酒店推荐、实用贴士

### 4. ✨ 旅行灵感

- **灵感分类导航** — 海岛度假、美食之旅、摄影之旅、治愈之旅
- **热门文章排行榜** — 发现最受欢迎的旅行内容
- **精选专题合集** — 深度主题内容聚合
- **瀑布流文章卡片** — 视觉舒适的浏览体验
- **用户评价轮播** — 真实用户分享（自动播放）
- **AI 偏好推荐** — 选择兴趣标签，AI 生成个性化推荐
- **文章/合集详情页** — 深度阅读体验，图文混排

### 5. 📋 我的行程

- **行程列表** — 每条显示目的地、旅行天数和创建时间
- **渐变色封面 + 城市图标** — 视觉化的行程卡片
- **按天查看详情** — Day 选项卡切换 + 滑动动画
- **活动图标匹配** — 美食、酒店、购物等智能图标识别
- **删除管理** — 支持删除不再需要的行程

### 6. 💬 历史对话

- **完整对话记录** — 浏览所有历史 AI 对话
- **精细管理** — 逐条删除或一键清空

### 7. 👤 个人中心

- **用户信息管理** — 昵称、头像编辑
- **旅行偏好设置** — 出行风格、预算范围、偏好季节、同行人员
- **统计面板** — Chart.js 可视化展示：行程数量、出行风格、注册时间、预算偏好

### 8. 🔐 用户认证

- **注册/登录** — JWT Token 认证，密码 BCrypt 加密
- **Token 自动管理** — localStorage 持久化 + Axios 拦截器自动附加
- **路由守卫** — 未登录自动跳转登录页，保障数据安全

---

## 技术栈

### 后端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 3.2.0 |
| AI 框架 | Spring AI | 1.0.0-M5 |
| 大语言模型 | DeepSeek | `deepseek-v4-flash` (OpenAI 兼容协议) |
| 数据库 | H2 (内存数据库) | — |
| ORM | Spring Data JPA / Hibernate | — |
| 安全认证 | Spring Security + JWT | jjwt 0.11.5 |
| 构建工具 | Maven | 3.6+ |
| 运行环境 | OpenJDK | 17+ |

### 前端

| 组件 | 技术 | 版本 |
|------|------|------|
| 框架 | Vue 3 (Composition API + `<script setup>`) | ^3.4.0 |
| 构建工具 | Vite | 5.x |
| 路由 | Vue Router 4 (Hash 模式) | ^4.2.0 |
| 状态管理 | Pinia | ^2.1.0 |
| HTTP 客户端 | Axios | ^1.6.0 |
| Markdown 渲染 | marked | ^9.1.0 |
| 数据可视化 | Chart.js + vue-chartjs | ^4.5.1 / ^5.3.3 |

---

## 快速开始

### 环境要求

| 依赖 | 版本要求 | 用途 |
|------|---------|------|
| **Java** | 17+ (OpenJDK) | 运行后端服务 |
| **Maven** | 3.6+ | 后端构建 |
| **Node.js** | 18+ | 前端构建与开发 |
| **npm** | 9+ | 前端依赖管理 |
| **DeepSeek API Key** | 在 [platform.deepseek.com](https://platform.deepseek.com/api_keys) 获取 | AI 对话调用 |

### 🚀 启动后端

```bash
# 1. 进入项目根目录
cd Travel

# 2. 配置 API Key（可选 — 用户也可在前端自行配置）
# Windows PowerShell:
$env:ANTHROPIC_AUTH_TOKEN="your-deepseek-api-key"

# macOS / Linux:
export ANTHROPIC_AUTH_TOKEN="your-deepseek-api-key"

# 3. 编译并启动
mvn clean install -DskipTests
mvn spring-boot:run
```

后端默认启动在 **`http://localhost:8081`**。

> **提示**：你也可以直接在 `application.properties` 中填入 API Key，或完全不配置服务端 Key，由用户在前端自行输入。

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

构建产物输出至 `frontend/dist/` 目录，可部署至任意静态资源服务器。

### 访问应用

打开浏览器访问 **`http://localhost:3000`**，注册账号后即可开始使用。

---

## 项目结构

```
Travel/
├── pom.xml                            # Maven 构建文件（源目录: after/src）
├── README.md                          # 项目文档（本文件）
├── .gitignore
│
├── after/                             # ======== Spring Boot 后端 ========
│   └── src/main/
│       ├── java/com/study/travel/
│       │   ├── TravelApplication.java         # @SpringBootApplication 应用入口
│       │   ├── config/
│       │   │   ├── AIConfig.java              # ChatClient + SystemPrompt + ChatMemory
│       │   │   ├── SecurityConfig.java        # JWT + Spring Security 过滤链
│       │   │   └── WebConfig.java             # CORS 跨域配置
│       │   ├── controller/
│       │   │   ├── AuthController.java        # POST /api/auth/register | /login
│       │   │   ├── ChatController.java        # SSE 流式对话 / 历史 / 行程 CRUD
│       │   │   └── ProfileController.java     # GET|PUT /api/user/profile
│       │   ├── dto/                           # 数据传输对象（请求/响应封装）
│       │   │   ├── ApiResult.java             # 统一响应 {code, message, data}
│       │   │   ├── AuthResponse.java          # {token, username, nickname}
│       │   │   ├── ChatRequest.java           # {message, apiKey?}
│       │   │   ├── ChatResponse.java          # {role, content, timestamp}
│       │   │   ├── LoginRequest.java          # {username, password}
│       │   │   ├── RegisterRequest.java       # {username, password, nickname?}
│       │   │   ├── PlanSaveRequest.java       # {city, days, content, preferences?}
│       │   │   └── ProfileUpdateRequest.java  # {nickname?, avatar?}
│       │   ├── entity/                        # JPA 实体类
│       │   │   ├── User.java                  # → users 表
│       │   │   ├── ChatMessage.java           # → chat_messages 表
│       │   │   └── TravelPlan.java            # → travel_plans 表
│       │   ├── repository/                    # 数据访问层 (Spring Data JPA)
│       │   │   ├── UserRepository.java
│       │   │   ├── ChatMessageRepository.java
│       │   │   └── TravelPlanRepository.java
│       │   ├── security/
│       │   │   ├── JwtAuthFilter.java         # OncePerRequestFilter Token 提取 & 校验
│       │   │   └── JwtUtil.java               # HS256 JWT 生成 & 验证
│       │   ├── service/
│       │   │   ├── AgentService.java          # ChatClient 封装（流式/同步 + 动态 API Key）
│       │   │   └── UserService.java           # 注册/登录/资料管理
│       │   └── tool/
│       │       ├── ToolRegistry.java          # 4 个 FunctionCallback 工具注册
│       │       └── TravelDataService.java     # 10 个城市模拟数据（景点/酒店/天气）
│       └── resources/
│           └── application.properties         # 服务端配置
│
└── frontend/                           # ======== Vue 3 前端 ========
    ├── index.html                     # SPA 入口 HTML
    ├── package.json                   # 前端依赖与脚本
    ├── vite.config.js                 # Vite 配置（含 API 代理）
    └── src/
        ├── main.js                    # Vue 应用入口
        ├── App.vue                    # 根组件
        ├── api/index.js               # Axios 实例 + JWT 拦截器
        ├── stores/auth.js             # Pinia 认证状态管理
        ├── router/index.js            # Hash 路由 + 导航守卫
        ├── components/
        │   ├── AppLayout.vue          # 主布局壳
        │   └── AiIcon.vue             # AI 装饰图标
        ├── composables/
        │   ├── useChat.js             # SSE 流式聊天 + API Key 管理
        │   ├── useToast.js            # Toast 通知
        │   └── usePlanCharts.js       # Chart.js 图表辅助
        ├── data/
        │   ├── destinations.js        # 16 个目的地卡片数据
        │   ├── articles.js            # 旅行文章数据
        │   └── images.js              # 图片资源引用
        ├── styles/main.css            # 全局样式
        └── views/
            ├── HomeView.vue           # 🏠 首页
            ├── LoginView.vue          # 🔐 登录/注册
            ├── ChatView.vue           # 💬 AI 智能规划（核心页面）
            ├── ExploreView.vue        # 🗺️ 发现目的地
            ├── DestinationDetailView.vue # 目的地详情
            ├── InspirationView.vue    # ✨ 旅行灵感
            ├── ArticleDetailView.vue  # 文章详情
            ├── CollectionDetailView.vue # 专题合集详情
            ├── PlansView.vue          # 📋 我的行程
            ├── HistoryView.vue        # 📋 历史对话
            └── ProfileView.vue        # 👤 个人中心
```

---

## 页面路由

| 路径 | 页面 | 说明 |
|------|------|------|
| `/` | 首页 | 搜索入口，功能导航 |
| `/login` | 登录/注册 | 玻璃拟态卡片，登录注册切换 |
| `/app/chat` | AI 智能规划 | ⭐ 核心页面 — 与 AI 对话规划旅行 |
| `/app/explore` | 发现目的地 | 目的地浏览与筛选 |
| `/app/explore/destination/:name` | 目的地详情 | 沉浸式详情页 |
| `/app/inspiration` | 旅行灵感 | 文章、专题、推荐 |
| `/app/inspiration/article/:slug` | 文章详情 | 深度阅读 |
| `/app/inspiration/collection/:slug` | 合集详情 | 专题内容 |
| `/app/plans` | 我的行程 | 已保存的旅行计划 |
| `/app/history` | 历史对话 | 聊天记录管理 |
| `/app/profile` | 个人中心 | 资料与偏好设置 |

> **路由守卫**：`/app/*` 下的所有页面需要登录认证，未登录用户自动跳转 `/login`。

---

## AI 架构

### 对话流程

```
┌─────────────────────┐     POST /api/chat/stream      ┌─────────────────────────┐
│     Vue 3 前端       │ ──── { message, apiKey } ────→  │   Spring Boot 后端      │
│                     │ ←─── SSE (text/event-stream) ─── │   ChatController       │
│  localStorage       │       data: 逐字 Token 内容     └───────────┬─────────────┘
│  deepseekApiKey     │                                               │
│  useChat()          │                                               ▼
│  composable         │                                   ┌─────────────────────────┐
└─────────────────────┘                                   │    AgentService         │
                                                          │                         │
                                                          │  createChatClient()     │
                                                          │  ┌─────────────────┐    │
                                                          │  │ OpenAiChatModel │    │
                                                          │  │ (DeepSeek 协议)  │    │
                                                          │  └────────┬────────┘    │
                                                          │           │             │
                                                          │  ┌────────▼────────┐    │
                                                          │  │   Function      │    │
                                                          │  │   Calling       │    │
                                                          │  │   (4 Tools)     │    │
                                                          │  └────────────────┘    │
                                                          └─────────────────────────┘
```

**两种 API Key 模式**：
- **无 `apiKey`** → 使用服务端配置的默认 ChatClient（从 `application.properties` 读取）
- **有 `apiKey`** → 后端动态创建 `OpenAiChatModel`，构建独立 ChatClient 实例
- 两种模式均保留完整的系统提示、对话记忆和工具调用能力

### 系统提示词

AI 旅行助手的角色定义在 `AIConfig.java` 的 `SYSTEM_PROMPT` 常量中，核心指令：

- **角色定位**：专业 AI 旅游规划助手，「旅行小助手」
- **工具触发规则**：
  - 用户提到目的地 + 天数 → 调用 `generateTravelPlan`
  - 用户问天气 → 调用 `getWeather`
  - 用户问景点 → 调用 `getScenicSpots`
  - 用户问酒店 → 调用 `getHotels`
- **回复风格**：使用适当的 emoji 和 Markdown 格式，提升可读性
- **主动推荐**：用户未明确目的地时，主动推荐热门城市
- **偏好记忆**：在多轮对话中记住并沿袭用户偏好

### 注册的 AI 工具（Function Calling）

Spring AI 将以下 Java 方法注册为 LLM 可调用的工具函数：

| 工具名称 | 功能描述 | 输入参数 | 返回内容 |
|---------|---------|---------|---------|
| `getScenicSpots` | 查询城市的景点列表 | `city: 城市名` | 名称、简介、票价、评分、开放时间、类别 |
| `getHotels` | 按预算推荐酒店 | `city: 城市名, budget?: 经济型/舒适型/品质型/奢华型` | 名称、档次、位置、价格、评分、特色 |
| `getWeather` | 查询城市天气 | `city: 城市名` | 温度、天气状况、出行建议 |
| `generateTravelPlan` | 生成完整行程 | `city: 城市名, days: 天数, preferences?: 偏好` | 每日详细安排 + 酒店推荐 + 天气提示 |

### 模拟数据

内置 **10 个城市**的模拟旅游数据，每个城市包含 6 个景点和 3 家酒店：

| 国内 | 北京 | 上海 | 杭州 | 成都 | 三亚 | 西安 | 丽江 | 厦门 |
|------|------|------|------|------|------|------|------|------|
| 国外 | 东京 | 巴黎 | | | | | | |

> 天气数据为随机生成，景点与酒店数据为精心设计的模拟数据，并非真实 API 调用结果。

---

## 用户自定义 API Key

用户可以在前端直接配置自己的 DeepSeek API Key，无需依赖服务端环境变量。

### 使用步骤

1. 进入 **AI 规划** 聊天页面（`/app/chat`）
2. 点击顶部右侧的 **API** 按钮（🔒 锁图标）
3. 在弹出的对话框输入你的 DeepSeek API Key
4. 点击「保存」— Key 存储在浏览器 `localStorage` 中
5. 配置成功后按钮旁显示绿色 **●** 指示点，表示已配置

### 安全说明

- ✅ **仅存本地** — API Key 仅保存在浏览器 `localStorage`，不上传至第三方服务器
- ✅ **内存使用** — 后端在内存中使用该 Key 创建临时 ChatClient，**不落盘、不记录日志**
- ✅ **HTTPS 传输** — Key 通过 HTTPS 请求体中的 `apiKey` 字段传递至后端
- ✅ **随时清除** — 用户可随时点击「清除 Key」，一键移除

### 两种模式对比

| 模式 | 配置方式 | 优势 | 适用场景 |
|------|---------|------|---------|
| 服务端默认 | `application.properties` / 环境变量 `ANTHROPIC_AUTH_TOKEN` | 用户无需操作，开箱即用 | 部署者预先配置共享 Key |
| 用户自定义 | 前端弹窗输入 | 用户用自己的 Key，额度独立，互不影响 | 个人使用、隐私敏感场景 |

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

| HTTP 状态码 | code | 说明 |
|-------------|------|------|
| 200 | 200 | 成功 |
| 400 | 400 | 请求参数错误 |
| 401 | 401 | 未认证或 Token 无效 |
| 403 | 403 | 无权限 |
| 500 | 500 | 服务器内部错误 |

### 🔐 认证接口（无需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `POST` | `/api/auth/register` | `{ username, password, nickname? }` | 注册新用户 |
| `POST` | `/api/auth/login` | `{ username, password }` | 登录，返回 JWT Token |

**登录响应示例**：
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "traveler",
    "nickname": "旅行者小明"
  }
}
```

### 💬 聊天接口（需 Token）

| 方法 | 路径 | 请求体 / 参数 | 说明 |
|------|------|---------------|------|
| `POST` | `/api/chat/stream` | `{ message, apiKey? }` | ⭐ 流式对话（SSE），实现打字机效果 |
| `POST` | `/api/chat` | `{ message, apiKey? }` | 同步对话，返回完整 AI 回复 |
| `GET` | `/api/chat/history` | — | 获取当前用户的聊天记录 |
| `DELETE` | `/api/chat/history/{id}` | — | 删除单条聊天记录 |
| `DELETE` | `/api/chat/history` | — | 清空所有聊天记录 |

> `apiKey` 为可选字段，不传则使用服务端配置的默认 Key。

### 📋 行程接口（需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `POST` | `/api/chat/plan/save` | `{ city, days, content, preferences? }` | 保存 AI 生成的行程 |
| `GET` | `/api/chat/plan/list` | — | 获取已保存的行程列表 |
| `DELETE` | `/api/chat/plan/{id}` | — | 删除指定行程 |

### 👤 用户接口（需 Token）

| 方法 | 路径 | 请求体 | 说明 |
|------|------|--------|------|
| `GET` | `/api/user/profile` | — | 获取用户信息 |
| `PUT` | `/api/user/profile` | `{ nickname, preferences? }` | 更新用户资料与偏好 |

---

## 数据模型

### User（用户表 → `users`）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `Long` | `@Id`, `@GeneratedValue` | 主键，自增 |
| `username` | `String` | `@Column(unique=true)`, `@NotBlank` | 用户名（唯一） |
| `password` | `String` | `@NotBlank` | BCrypt 加密密码 |
| `nickname` | `String` | 可空 | 用户昵称 |
| `createTime` | `LocalDateTime` | `@PrePersist` | 注册时间，自动生成 |

### ChatMessage（聊天消息表 → `chat_messages`）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `Long` | `@Id`, `@GeneratedValue` | 主键，自增 |
| `userId` | `Long` | — | 用户 ID |
| `role` | `String` | — | `"user"` 或 `"assistant"` |
| `content` | `String` | `@Lob`, `@Column(columnDefinition="TEXT")` | 消息内容 |
| `createTime` | `LocalDateTime` | `@PrePersist` | 发送时间，自动生成 |

### TravelPlan（旅行计划表 → `travel_plans`）

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `Long` | `@Id`, `@GeneratedValue` | 主键，自增 |
| `userId` | `Long` | — | 用户 ID |
| `city` | `String` | — | 目的地城市 |
| `days` | `Integer` | — | 旅行天数 |
| `content` | `String` | `@Lob`, `@Column(columnDefinition="TEXT")` | AI 生成的 Markdown 行程内容 |
| `preferences` | `String` | 可空 | 用户旅行偏好 |
| `createTime` | `LocalDateTime` | `@PrePersist` | 创建时间，自动生成 |

---

## 配置指南

### application.properties 核心配置

```properties
# ====== 服务端口 ======
server.port=8081

# ====== 数据库（H2 内存模式） ======
spring.datasource.url=jdbc:h2:mem:travel_agent;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# ====== JPA / Hibernate ======
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.open-in-view=false

# ====== JWT 安全（生产环境请更换密钥！） ======
app.jwt.secret=TravelAssistant2024SecretKeyForJWTTokenGenerationAndValidation
app.jwt.expiration=86400000    # 24 小时，单位毫秒

# ====== LLM 配置（OpenAI 兼容协议 → DeepSeek） ======
spring.ai.openai.base-url=https://api.deepseek.com
spring.ai.openai.api-key=${ANTHROPIC_AUTH_TOKEN}
spring.ai.openai.chat.options.model=deepseek-v4-flash
spring.ai.openai.chat.options.temperature=0.7
spring.ai.openai.chat.options.max-tokens=2000
```

### 更换 LLM 提供商

本项目基于 Spring AI 的 OpenAI 兼容协议开发，理论上支持任何兼容 OpenAI API 的 LLM 提供商：

```properties
# 通义千问（阿里云）
spring.ai.openai.base-url=https://dashscope.aliyuncs.com/compatible-mode/v1
spring.ai.openai.chat.options.model=qwen-plus

# OpenAI GPT
spring.ai.openai.base-url=https://api.openai.com/v1
spring.ai.openai.chat.options.model=gpt-4o

# Azure OpenAI
spring.ai.openai.base-url=https://<your-resource>.openai.azure.com
spring.ai.openai.chat.options.model=gpt-4
```

### 前端 API 代理配置（vite.config.js）

```js
export default defineConfig({
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true
      }
    }
  }
})
```

### 数据库持久化配置（替换 H2）

如需数据持久化，替换为 MySQL 或 PostgreSQL：

```properties
# MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/travel_agent?useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=your-password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/travel_agent
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=your-password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

> 注意：更换数据库后，需在 `pom.xml` 中添加对应的 JDBC 驱动依赖。

---

## 常见问题

### Q: 启动后端报错 "API Key not configured"？

**A:** 这是 DeepSeek API Key 未配置导致的。有三种解决方式：

1. **设置环境变量**（推荐）：
   ```bash
   # Windows PowerShell
   $env:ANTHROPIC_AUTH_TOKEN="sk-xxxxxxxxxxxxxxxx"
   # macOS / Linux
   export ANTHROPIC_AUTH_TOKEN="sk-xxxxxxxxxxxxxxxx"
   ```
2. **直接修改配置文件**：在 `application.properties` 中将 `${ANTHROPIC_AUTH_TOKEN}` 替换为你的真实 Key
3. **前端配置**：不配置服务端 Key，启动后在浏览器中自行输入（详见 [用户自定义 API Key](#用户自定义-api-key)）

### Q: 如何在前端配置自己的 API Key？

**A:** 进入 AI 规划聊天页（`/app/chat`）→ 点击顶部右侧 **API 按钮**（🔒 锁图标）→ 输入 DeepSeek API Key → 点击保存。Key 仅存储在浏览器 `localStorage` 中，安全可控。

### Q: 是否必须联网才能使用？

**A:** AI 对话需调用 DeepSeek API，**必须联网**。但首页浏览、目的地发现、旅行灵感等静态页面内容可离线查看。

### Q: 数据会丢失吗？

**A:** 当前使用 H2 **内存数据库**，服务重启后所有数据（用户、聊天记录、行程）都会丢失。如需持久化，请替换为 MySQL 或 PostgreSQL（参见 [数据库持久化配置](#数据库持久化配置替换-h2)）。

### Q: 如何将模拟数据替换为真实 API 数据？

**A:** `TravelDataService.java` 中的景点、酒店、天气数据均为模拟静态数据。可在此类中调用第三方 API（如高德地图 POI 查询、携程开放平台、OpenWeatherMap 等）替代静态数据。

### Q: 如何增加新的支持城市？

**A:** 需要修改两处：
- **后端**：在 `TravelDataService.java` 的静态代码块中按现有格式添加景点和酒店数据
- **前端**：在 `frontend/src/data/destinations.js` 中添加对应城市的展示条目

---

## 扩展方向

### 近期可优化

- [ ] **数据库持久化** — 替换 H2 为 MySQL / PostgreSQL，避免重启数据丢失
- [ ] **真实旅游 API** — 接入高德地图、携程、OpenWeather 等第三方数据源
- [ ] **Redis 对话缓存** — 替代内存 ChatMemory，支持分布式部署

### 中长期规划

- [ ] **文件上传** — 支持用户上传自定义头像
- [ ] **社交功能** — 行程分享、用户评论、游记发布、点赞互动
- [ ] **多语言 i18n** — 国际化支持，中英文切换
- [ ] **行程导出** — PDF / 图片格式导出，方便离线查看
- [ ] **OAuth 登录** — 微信 / Google / GitHub 第三方登录
- [ ] **AI 图片生成** — 接入 DALL·E 或 Stable Diffusion，为行程生成配图
- [ ] **移动端 App** — 基于 PWA 或 Uni-app 打包为移动应用

---

## 开源许可

本项目仅供学习和交流使用。如需商业使用，请自行评估。

---

<div align="center">
  <sub>Made with ❤️ by 游伴 AI Team</sub>
</div>
