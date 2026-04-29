package com.preventhub.analytics.dto;

public record GlobalKpiResponse(
        long totalCampaigns,
        double engagementRate,
        double participationRate,
        double coverageRate,
        double estimatedHealthRoi
) {
}
