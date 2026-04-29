package com.preventhub.risk.dto;

import com.preventhub.risk.domain.RiskLevel;

import java.util.List;

public record CitizenRiskView(
        Long citizenId,
        String fullName,
        Integer age,
        String gender,
        String city,
        String region,
        RiskLevel riskLevel,
        int riskScore,
        List<String> chronicConditions
) {
}
