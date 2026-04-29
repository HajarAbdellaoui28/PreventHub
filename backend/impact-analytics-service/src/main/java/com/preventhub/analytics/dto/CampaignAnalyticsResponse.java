package com.preventhub.analytics.dto;

public record CampaignAnalyticsResponse(
        Long campaignId,
        String campaignTitle,
        long audienceSize,
        long opened,
        long participated,
        long droppedOut,
        double effectivenessScore,
        double estimatedSavings
) {
}
