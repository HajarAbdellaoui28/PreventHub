package com.preventhub.engagement.dto;

public record EngagementStatsResponse(
        long totalRecords,
        long openedCount,
        long enrolledCount,
        long participatedCount,
        long droppedOutCount,
        double openRate,
        double participationRate,
        double dropoutRate,
        double averageEngagementScore
) {
}
