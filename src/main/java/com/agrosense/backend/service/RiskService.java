package com.agrosense.backend.service;

import com.agrosense.backend.dto.risk.RiskAnalyzeRequest;
import com.agrosense.backend.dto.risk.RiskAnalyzeResponse;
import com.agrosense.backend.dto.risk.RiskHistoryItemDto;

import java.util.List;

public interface RiskService {
    RiskAnalyzeResponse analyze(RiskAnalyzeRequest request);

    List<RiskHistoryItemDto> history(Long farmerId);
}
