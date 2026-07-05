package com.study.travel.controller;

import com.study.travel.dto.ApiResult;
import com.study.travel.dto.PlanSaveRequest;
import com.study.travel.entity.TravelPlan;
import com.study.travel.entity.User;
import com.study.travel.repository.TravelPlanRepository;
import com.study.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResult<List<TravelPlan>>> getPlans(Authentication auth) {
        Long userId = getUserId(auth);
        List<TravelPlan> plans = travelPlanRepository.findByUserIdOrderByCreateTimeDesc(userId);
        return ResponseEntity.ok(ApiResult.success(plans));
    }

    @PostMapping
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> deletePlan(Authentication auth, @PathVariable Long id) {
        travelPlanRepository.deleteById(id);
        return ResponseEntity.ok(ApiResult.success("删除成功", null));
    }

    private Long getUserId(Authentication auth) {
        if (auth == null) return 1L;
        return userRepository.findByUsername(auth.getName())
                .map(User::getId)
                .orElse(1L);
    }
}
