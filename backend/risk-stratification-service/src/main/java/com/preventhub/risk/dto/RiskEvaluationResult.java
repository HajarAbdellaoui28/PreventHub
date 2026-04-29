package com.preventhub.risk.dto;

import com.preventhub.risk.domain.RiskLevel;

import java.util.List;

public record RiskEvaluationResult(
        Long citizenId,
        String fullName,
        RiskLevel riskLevel,
        int score,
        List<String> contributingFactors
) {
}
