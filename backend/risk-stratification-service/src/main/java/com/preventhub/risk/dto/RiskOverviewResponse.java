package com.preventhub.risk.dto;

public record RiskOverviewResponse(
        long totalCitizens,
        long highRisk,
        long mediumRisk,
        long lowRisk,
        String topRiskRegion,
        String topRiskCondition
) {
}
