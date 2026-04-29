package com.preventhub.risk.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RiskEvaluationRequest(
        @NotNull Long citizenId,
        List<String> campaignConditions
) {
}
