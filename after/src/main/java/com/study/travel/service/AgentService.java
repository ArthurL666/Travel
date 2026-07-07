package com.study.travel.service;

import com.study.travel.config.AIConfig;
import com.study.travel.entity.ChatMessage;
import com.study.travel.repository.ChatMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 智能体核心服务 — Spring AI ChatClient 驱动
 *
 * 架构：ChatClient = LLM + SystemPrompt + Tools + ChatMemory
 * AI 根据用户输入自主决策调用哪些 Tool，整合结果后生成回复
 * 支持服务端配置的 API Key 和用户自定义的 API Key 两种模式
 */
@Service
public class AgentService {

    private static final Logger log = LoggerFactory.getLogger(AgentService.class);

    // ===== 默认 ChatClient（服务端配置的 API Key） =====
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // ===== 动态 ChatClient 的依赖 =====
    @Autowired
    private ChatMemory chatMemory;

    @Autowired
    private MessageChatMemoryAdvisor memoryAdvisor;

    @Autowired(required = false)
    private List<FunctionCallback> toolCallbacks;

    // ===== DeepSeek API 配置（从 application.properties 读取） =====
    @Value("${spring.ai.openai.base-url}")
    private String baseUrl;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    @Value("${spring.ai.openai.chat.options.temperature:0.7}")
    private double temperature;

    @Value("${spring.ai.openai.chat.options.max-tokens:2000}")
    private int maxTokens;

    /**
     * 使用用户提供的 API Key 动态创建 ChatClient
     * 如果 apiKey 为空或空白，则返回默认的服务端配置 ChatClient
     */
    private ChatClient createChatClient(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            return chatClient; // 使用服务端配置的 API Key
        }

        // 构建 OpenAI 兼容 API 客户端（DeepSeek 使用 OpenAI 兼容协议）
        OpenAiApi openAiApi = new OpenAiApi(baseUrl, apiKey);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(model)
                .withTemperature(temperature)
                .withMaxTokens(maxTokens)
                .build();
        OpenAiChatModel chatModel = new OpenAiChatModel(openAiApi, options);

        // 构建 ChatClient：注入系统提示 + 记忆管理器 + 工具回调
        ChatClient.Builder builder = ChatClient.builder(chatModel)
                .defaultSystem(AIConfig.SYSTEM_PROMPT)
                .defaultAdvisors(memoryAdvisor);

        // 注入所有注册的 FunctionCallback 工具
        if (toolCallbacks != null && !toolCallbacks.isEmpty()) {
            builder.defaultFunctions(toolCallbacks.toArray(new FunctionCallback[0]));
        }

        return builder.build();
    }

    // ==================== 流式对话（SSE） ====================

    /**
     * 默认流式对话 — 使用服务端配置的 API Key
     */
    public Flux<String> chatStream(String userMessage, Long userId, String conversationId) {
        return chatStream(userMessage, userId, conversationId, null);
    }

    /**
     * 流式对话 — 支持用户自定义 API Key
     *
     * @param apiKey 用户传入的 DeepSeek API Key，null 或空则使用服务端配置
     */
    public Flux<String> chatStream(String userMessage, Long userId, String conversationId, String apiKey) {
        saveMessage(userId, "user", userMessage);

        ChatClient client = createChatClient(apiKey);
        log.info("Using ChatClient with apiKey={}", apiKey != null && !apiKey.isBlank() ? "user-provided" : "server-default");

        StringBuilder fullResponse = new StringBuilder();
        return client.prompt()
                .user(userMessage)
                .advisors(a -> a.param("chat_memory_conversation_id", conversationId))
                .stream()
                .content()
                .doOnNext(chunk -> fullResponse.append(chunk))
                .doOnComplete(() -> {
                    String content = fullResponse.toString();
                    if (!content.isEmpty()) {
                        saveMessage(userId, "assistant", content);
                        log.info("AI response saved ({} chars)", content.length());
                    }
                })
                .doOnError(e -> log.error("Spring AI streaming error", e));
    }

    // ==================== 同步对话 ====================

    /**
     * 默认同步对话 — 使用服务端配置的 API Key
     */
    public String chat(String userMessage, Long userId, String conversationId) {
        return chat(userMessage, userId, conversationId, null);
    }

    /**
     * 同步对话 — 支持用户自定义 API Key
     *
     * @param apiKey 用户传入的 DeepSeek API Key，null 或空则使用服务端配置
     */
    public String chat(String userMessage, Long userId, String conversationId, String apiKey) {
        saveMessage(userId, "user", userMessage);

        ChatClient client = createChatClient(apiKey);
        log.info("Using ChatClient with apiKey={}", apiKey != null && !apiKey.isBlank() ? "user-provided" : "server-default");

        String content = client.prompt()
                .user(userMessage)
                .advisors(a -> a.param("chat_memory_conversation_id", conversationId))
                .call()
                .content();
        if (content != null && !content.isEmpty()) {
            saveMessage(userId, "assistant", content);
        }
        return content;
    }

    private void saveMessage(Long userId, String role, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setUserId(userId);
        msg.setRole(role);
        msg.setContent(content);
        chatMessageRepository.save(msg);
    }
}
