package com.agrosense.backend.controller;

import com.agrosense.backend.dto.ApiResponse;
import com.agrosense.backend.dto.risk.RiskAnalyzeRequest;
import com.agrosense.backend.dto.risk.RiskAnalyzeResponse;
import com.agrosense.backend.dto.risk.RiskHistoryItemDto;
import com.agrosense.backend.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @PostMapping("/analyze")
    public ApiResponse<RiskAnalyzeResponse> analyze(@RequestBody RiskAnalyzeRequest request) {
        return new ApiResponse<>(true, "Risk analysis complete", riskService.analyze(request));
    }

    @GetMapping("/history")
    public ApiResponse<List<RiskHistoryItemDto>> history(@RequestParam Long farmerId) {
        return new ApiResponse<>(true, "Risk history fetched", riskService.history(farmerId));
    }
}
