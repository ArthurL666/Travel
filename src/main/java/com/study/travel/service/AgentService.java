package com.study.travel.service;

import com.study.travel.entity.ChatMessage;
import com.study.travel.repository.ChatMessageRepository;
import com.study.travel.tool.TravelDataService;
import com.study.travel.tool.TravelDataService.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 智能体核心服务 — Spring AI ChatClient 驱动
 *
 * 架构：ChatClient = LLM + SystemPrompt + Tools + ChatMemory
 * AI 根据用户输入自主决策调用哪些 Tool，整合结果后生成回复
 */
@Service
public class AgentService {

    private static final Logger log = LoggerFactory.getLogger(AgentService.class);

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private TravelDataService dataService;

    @Value("${app.ai.fallback-enabled:true}")
    private boolean fallbackEnabled;

    /**
     * Spring AI 流式对话 — Flux<SSE>
     * AI 自主决策 Tool Calling 链：分析意图 → 调用工具 → 整合结果 → 流式输出
     */
    public Flux<String> chatStream(String userMessage, Long userId, String conversationId) {
        // 保存用户消息
        saveMessage(userId, "user", userMessage);

        // 如果配置了真实LLM，使用 Spring AI ChatClient
        if (!fallbackEnabled) {
            return chatClient.prompt()
                    .user(userMessage)
                    .advisors(a -> a.param("chat_memory_conversation_id", conversationId))
                    .stream()
                    .content()
                    .doOnComplete(() -> {
                        // Streaming完成后需要额外处理
                    })
                    .doOnError(e -> log.error("Spring AI streaming error", e));
        }

        // Fallback: 模拟AI响应（无LLM API Key时使用）
        String response = generateSimulatedResponse(userMessage, userId);
        return Flux.just(response);
    }

    /**
     * 同步对话
     */
    public String chat(String userMessage, Long userId, String conversationId) {
        saveMessage(userId, "user", userMessage);

        if (!fallbackEnabled) {
            try {
                return chatClient.prompt()
                        .user(userMessage)
                        .advisors(a -> a.param("chat_memory_conversation_id", conversationId))
                        .call()
                        .content();
            } catch (Exception e) {
                log.warn("Spring AI call failed, falling back to simulated: {}", e.getMessage());
            }
        }

        String response = generateSimulatedResponse(userMessage, userId);
        saveMessage(userId, "assistant", response);
        return response;
    }

    // ==================== 模拟AI响应 (Fallback) ====================

    private String generateSimulatedResponse(String msg, Long userId) {
        String lower = msg.trim().toLowerCase();

        // 意图路由 — 模拟 Spring AI 的 Tool Calling 决策
        if (matches(lower, "你好", "hi", "hello", "嗨", "在吗")) return greeting();
        if (matches(lower, "天气")) return handleWeather(lower);
        if (matches(lower, "酒店", "住宿", "住")) return handleHotels(lower);
        if (matches(lower, "景点", "好玩", "必去")) return handleScenic(lower);

        // 行程规划：城市+天数
        String city = dataService.findCity(msg);
        if (city != null) {
            int days = extractDays(lower);
            if (days > 0) return generatePlan(city, days, lower);
            if (matches(lower, "推荐", "介绍", "有什么", "玩", "旅游", "去")) return cityIntro(city);
        }

        if (matches(lower, "预算", "多少钱", "花费", "便宜")) return budgetGuide();
        if (matches(lower, "推荐", "建议", "哪里好")) return recommendation();
        if (matches(lower, "行程", "计划", "我的", "保存")) return "📋 您可以通过左侧菜单「我的行程」查看和管理已保存的旅行计划～\n\n如果想规划新行程，直接告诉我目的地和天数即可！✈️";

        return defaultReply();
    }

    // ---- 意图处理 ----

    private String greeting() {
        return "👋 你好呀！我是你的**AI旅游规划助手** ✈️\n\n"
            + "我可以帮你：\n🌍 **规划旅行** | 🏨 **推荐酒店** | ⛅ **查询天气** | 🎯 **推荐景点** | 💰 **预算建议**\n\n"
            + "现在，告诉我你想去哪里玩吧～ 🗺️";
    }

    private String handleWeather(String msg) {
        String city = dataService.findCity(msg);
        if (city == null) return "🌤️ 请告诉我你想查询哪个城市的天气呢？比如：**查一下三亚的天气**";
        return "⛅ " + dataService.getWeather(city) + "\n\n💡 需要我帮你规划" + city + "的行程吗？";
    }

    private String handleHotels(String msg) {
        String city = dataService.findCity(msg);
        if (city == null) return "🏨 请告诉我你想在哪个城市住宿呢？比如：**推荐成都的酒店**";
        List<HotelInfo> hotels = dataService.getHotels(city);
        if (hotels.isEmpty()) return "😅 抱歉，暂无" + city + "的酒店数据";
        StringBuilder sb = new StringBuilder("🏨 **" + city + "酒店推荐**\n\n");
        for (int i = 0; i < hotels.size(); i++) {
            HotelInfo h = hotels.get(i);
            sb.append("**").append(i + 1).append(". ").append(h.name()).append("**\n")
              .append("   ⭐ ").append(h.level()).append(" | 💰 ¥").append(h.price())
              .append("/晚 | 📍 ").append(h.location()).append("\n")
              .append("   评分：").append("★".repeat((int) h.rating())).append(" ").append(h.rating()).append("\n\n");
        }
        sb.append("💡 告诉我你的预算范围，我帮你筛选更合适的～");
        return sb.toString();
    }

    private String handleScenic(String msg) {
        String city = dataService.findCity(msg);
        if (city == null) return "🎯 请告诉我你想了解哪个城市的景点呢？\n\n支持：北京 | 上海 | 杭州 | 成都 | 三亚 | 西安 | 丽江 | 厦门 | 东京 | 巴黎";
        List<ScenicInfo> spots = dataService.getScenicSpots(city);
        if (spots.isEmpty()) return "😅 暂无" + city + "的景点数据";
        Map<String, List<ScenicInfo>> grouped = spots.stream().collect(Collectors.groupingBy(ScenicInfo::category, LinkedHashMap::new, Collectors.toList()));
        StringBuilder sb = new StringBuilder("🎯 **" + city + "热门景点**\n\n");
        for (var e : grouped.entrySet()) {
            String emoji = switch (e.getKey()) {
                case "历史文化" -> "🏛️"; case "园林自然" -> "🌿"; case "美食购物" -> "🍜";
                case "主题乐园" -> "🎢"; case "海滨度假" -> "🏖️"; case "城市景观" -> "🌆";
                case "艺术文创" -> "🎨"; case "自然体验" -> "🏔️"; default -> "📍";
            };
            sb.append("### ").append(emoji).append(" ").append(e.getKey()).append("\n");
            for (ScenicInfo s : e.getValue()) {
                sb.append("- **").append(s.name()).append("** ⭐").append(s.rating())
                  .append(" | ").append(s.price()).append(" | ").append(s.hours()).append("\n")
                  .append("  ").append(s.description()).append("\n");
            }
            sb.append("\n");
        }
        sb.append("💡 看到感兴趣的景点了吗？告诉我玩几天，帮你规划！");
        return sb.toString();
    }

    private String generatePlan(String city, int days, String msg) {
        List<ScenicInfo> spots = dataService.getScenicSpots(city);
        List<HotelInfo> hotels = dataService.getHotels(city);
        if (spots.isEmpty()) return "😅 暂不支持" + city + "。支持：北京、上海、杭州、成都、三亚、西安、丽江、厦门、东京、巴黎";

        String pref = extractPref(msg);
        StringBuilder sb = new StringBuilder();
        sb.append("🌟 **").append(city).append(" ").append(days).append("天旅行计划** 🌟\n\n");
        if (!pref.isEmpty()) sb.append("> 🎯 偏好：").append(pref).append("\n\n");
        sb.append("⛅ ").append(dataService.getWeather(city)).append("\n\n---\n\n");

        int si = 0;
        String[] times = {"上午", "下午", "晚上"};
        for (int d = 1; d <= days; d++) {
            sb.append("### 📅 第").append(d).append("天\n\n");
            for (int t = 0; t < Math.min(3, Math.max(2, spots.size() / days)); t++) {
                if (t < 2 && si < spots.size()) {
                    ScenicInfo s = spots.get(si % spots.size());
                    sb.append("**").append(times[t]).append("**：").append(s.name())
                      .append(" | ⭐").append(s.rating()).append(" | ").append(s.price()).append("\n")
                      .append("> ").append(s.description()).append("\n\n");
                }
                si++;
            }
            si = (d * 2) % spots.size();
        }

        sb.append("---\n\n### 🏨 住宿推荐\n\n");
        for (int i = 0; i < Math.min(2, hotels.size()); i++) {
            HotelInfo h = hotels.get(i);
            sb.append("**").append(h.name()).append("** | ").append(h.level())
              .append(" | 💰 ¥").append(h.price()).append("/晚 | 📍 ").append(h.location()).append("\n\n");
        }

        sb.append("💡 可以告诉我 **太赶了** / **想加购物** / **预算有限** 来调整行程～");
        return sb.toString();
    }

    private String cityIntro(String city) {
        List<ScenicInfo> spots = dataService.getScenicSpots(city);
        StringBuilder sb = new StringBuilder("🏙️ **" + city + "** 是个超棒的目的地！\n\n热门景点：\n");
        for (int i = 0; i < Math.min(5, spots.size()); i++) {
            ScenicInfo s = spots.get(i);
            sb.append(i + 1).append(". **").append(s.name()).append("** ⭐").append(s.rating()).append("\n");
        }
        sb.append("\n⛅ ").append(dataService.getWeather(city)).append("\n\n");
        sb.append("🤔 你打算玩几天呢？告诉我天数，马上帮你规划！");
        return sb.toString();
    }

    private String budgetGuide() {
        return "💰 **旅行预算参考**\n\n| 类型 | 人均/天 | 特点 |\n|------|--------|------|\n"
            + "| 🎒 穷游 | ¥200-400 | 青旅+公共交通 |\n| 🏨 舒适 | ¥500-1000 | 三星+打车+餐馆 |\n"
            + "| 🌟 品质 | ¥1000-2000 | 四星+包车 |\n| 👑 奢华 | ¥2000+ | 五星+专车 |\n\n"
            + "💡 比如：**预算3000元，去成都3天**";
    }

    private String recommendation() {
        StringBuilder sb = new StringBuilder("🌟 **精选目的地推荐**\n\n");
        Map<String, String> recs = new LinkedHashMap<>();
        recs.put("成都", "🐼 美食+大熊猫，悠闲慢生活");
        recs.put("三亚", "🏖️ 阳光沙滩，海岛度假首选");
        recs.put("杭州", "🌿 西湖美景，江南韵味");
        recs.put("西安", "🏛️ 千年古都，历史之旅");
        recs.put("东京", "🗼 现代与传统融合");
        recs.put("巴黎", "🎨 艺术与浪漫之都");
        recs.forEach((k, v) -> sb.append("- **").append(k).append("**：").append(v).append("\n"));
        sb.append("\n🎯 选一个感兴趣的城市，告诉我玩几天吧！");
        return sb.toString();
    }

    private String defaultReply() {
        String[] replies = {
            "🤔 为了给你最好的建议，请告诉我：\n📍 **目的地** — 想去哪个城市？\n📅 **天数** — 计划玩几天？\n比如：**帮我规划成都3天美食之旅** 🍜",
            "✈️ 一场说走就走的旅行，从制定计划开始！\n\n支持：北京 · 上海 · 杭州 · 成都 · 三亚 · 西安 · 丽江 · 厦门 · 东京 · 巴黎\n\n告诉我你想去哪里～",
            "🌟 想要一次完美的旅行？只需回答：\n1️⃣ 想去哪里？ 2️⃣ 玩几天？ 3️⃣ 喜欢什么类型？\n然后我就能帮你生成专属旅行计划！✨"
        };
        return replies[new Random().nextInt(replies.length)];
    }

    // ---- 辅助 ----

    private int extractDays(String msg) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)\\s*天").matcher(msg);
        if (m.find()) return Math.min(Integer.parseInt(m.group(1)), 14);
        Map<String, Integer> cn = new HashMap<>();
        cn.put("一",1); cn.put("两",2); cn.put("二",2); cn.put("三",3); cn.put("四",4); cn.put("五",5);
        cn.put("六",6); cn.put("七",7); cn.put("八",8); cn.put("九",9); cn.put("十",10);
        for (var e : cn.entrySet()) if (msg.contains(e.getKey() + "天")) return e.getValue();
        return matches(msg, "规划", "行程", "计划", "旅行") ? -1 : -1;
    }

    private String extractPref(String msg) {
        StringBuilder p = new StringBuilder();
        if (matches(msg, "美食", "吃", "吃货")) p.append("美食 ");
        if (matches(msg, "自然", "风景", "山", "海", "户外")) p.append("自然风光 ");
        if (matches(msg, "文化", "历史", "博物", "古迹")) p.append("历史文化 ");
        if (matches(msg, "购物", "买")) p.append("购物 ");
        if (matches(msg, "艺术", "文艺")) p.append("艺术文创 ");
        return p.toString().trim();
    }

    private boolean matches(String msg, String... keywords) {
        for (String kw : keywords) if (msg.contains(kw)) return true;
        return false;
    }

    private void saveMessage(Long userId, String role, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setUserId(userId); msg.setRole(role); msg.setContent(content);
        chatMessageRepository.save(msg);
    }
}
