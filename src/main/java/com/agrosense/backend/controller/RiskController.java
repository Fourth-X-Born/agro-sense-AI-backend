package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.risk.RiskAnalyzeRequest;
import com.agrosense.backend.dto.risk.RiskAnalyzeResponse;
import com.agrosense.backend.dto.risk.RiskHistoryItemDto;
import com.agrosense.backend.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @PostMapping("/analyze")
    public ApiResponse<RiskAnalyzeResponse> analyze(Authentication authentication, @RequestBody RiskAnalyzeRequest request) {
        request.setFarmerId((Long) authentication.getPrincipal());
        return new ApiResponse<>(true, "Risk analysis complete", riskService.analyze(request));
    }

    @GetMapping("/history")
    public ApiResponse<List<RiskHistoryItemDto>> history(Authentication authentication) {
        Long farmerId = (Long) authentication.getPrincipal();
        return new ApiResponse<>(true, "Risk history fetched", riskService.history(farmerId));
    }
}
