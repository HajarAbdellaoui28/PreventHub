package com.preventhub.engagement.dto;

public record CampaignEngagementSummary(
        Long campaignId,
        long totalTargets,
        long opened,
        long enrolled,
        long participated,
        long droppedOut,
        double campaignPerformanceScore
) {
}
