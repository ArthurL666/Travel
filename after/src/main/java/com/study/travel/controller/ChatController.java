package com.study.travel.controller;

import com.study.travel.dto.ApiResult;
import com.study.travel.dto.ChatRequest;
import com.study.travel.dto.ChatResponse;
import com.study.travel.dto.PlanSaveRequest;
import com.study.travel.entity.ChatMessage;
import com.study.travel.entity.TravelPlan;
import com.study.travel.entity.User;
import com.study.travel.repository.ChatMessageRepository;
import com.study.travel.repository.TravelPlanRepository;
import com.study.travel.repository.UserRepository;
import com.study.travel.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private UserRepository userRepository;

    private Long getUserId(Authentication auth) {
        if (auth == null) return 1L;
        return userRepository.findByUsername(auth.getName())
                .map(User::getId)
                .orElse(1L);
    }

    // ==================== 历史消息 ====================

    @GetMapping("/history")
    public ResponseEntity<ApiResult<List<ChatMessage>>> getHistory(Authentication auth) {
        Long userId = getUserId(auth);
        List<ChatMessage> messages = chatMessageRepository.findByUserIdOrderByCreateTimeAsc(userId);
        return ResponseEntity.ok(ApiResult.success(messages));
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<ApiResult<Void>> deleteHistory(@PathVariable Long id) {
        chatMessageRepository.deleteById(id);
        return ResponseEntity.ok(ApiResult.success("删除成功", null));
    }

    @DeleteMapping("/history")
    public ResponseEntity<ApiResult<Void>> clearHistory(Authentication auth) {
        Long userId = getUserId(auth);
        chatMessageRepository.deleteByUserId(userId);
        return ResponseEntity.ok(ApiResult.success("已清空", null));
    }

    // ==================== 行程规划 ====================

    @PostMapping("/plan/save")
    public ResponseEntity<ApiResult<TravelPlan>> savePlan(Authentication auth,
                                                          @Valid @RequestBody PlanSaveRequest request) {
        Long userId = getUserId(auth);
        TravelPlan plan = new TravelPlan();
        plan.setUserId(userId);
        plan.setCity(request.getCity());
        plan.setDays(request.getDays());
        plan.setContent(request.getContent());
        plan.setPreferences(request.getPreferences());
        travelPlanRepository.save(plan);
        return ResponseEntity.ok(ApiResult.success("保存成功", plan));
    }

    @GetMapping("/plan/list")
    public ResponseEntity<ApiResult<List<TravelPlan>>> listPlans(Authentication auth) {
        Long userId = getUserId(auth);
        List<TravelPlan> plans = travelPlanRepository.findByUserIdOrderByCreateTimeDesc(userId);
        return ResponseEntity.ok(ApiResult.success(plans));
    }

    @DeleteMapping("/plan/{id}")
    public ResponseEntity<ApiResult<Void>> deletePlan(@PathVariable Long id) {
        travelPlanRepository.deleteById(id);
        return ResponseEntity.ok(ApiResult.success("删除成功", null));
    }

    // ==================== 聊天 ====================

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@Valid @RequestBody ChatRequest request,
                                   Authentication auth) {
        Long userId = getUserId(auth);
        String username = auth != null ? auth.getName() : "anonymous";
        String conversationId = "conv_" + username;
        return agentService.chatStream(request.getMessage(), userId, conversationId);
    }

    @PostMapping
    public ResponseEntity<ApiResult<ChatResponse>> chat(@Valid @RequestBody ChatRequest request,
                                                        Authentication auth) {
        Long userId = getUserId(auth);
        String username = auth != null ? auth.getName() : "anonymous";
        String conversationId = "conv_" + username;
        String response = agentService.chat(request.getMessage(), userId, conversationId);
        ChatResponse chatResp = new ChatResponse("assistant", response,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return ResponseEntity.ok(ApiResult.success(chatResp));
    }
}
