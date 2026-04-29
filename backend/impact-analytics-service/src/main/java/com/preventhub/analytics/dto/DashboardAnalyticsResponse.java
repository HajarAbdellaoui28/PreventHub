package com.preventhub.analytics.dto;

import java.util.List;

public record DashboardAnalyticsResponse(
        GlobalKpiResponse globalKpis,
        List<CampaignKpiView> activeCampaigns,
        RiskDistributionView riskDistribution,
        List<String> strategicInsights
) {
    public record CampaignKpiView(
            Long id,
            String title,
            String status,
            int currentAudienceSize
    ) {
    }

    public record RiskDistributionView(
            long highRisk,
            long mediumRisk,
            long lowRisk,
            String topRiskRegion
    ) {
    }
}
