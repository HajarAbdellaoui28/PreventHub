package com.preventhub.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "engagement-tracking-service")
public interface EngagementClient {

    @GetMapping("/api/engagement/stats")
    EngagementStats stats();

    @GetMapping("/api/engagement/campaigns/{campaignId}/summary")
    CampaignSummary campaignSummary(@PathVariable("campaignId") Long campaignId);

    record EngagementStats(
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

    record CampaignSummary(
            Long campaignId,
            long totalTargets,
            long opened,
            long enrolled,
            long participated,
            long droppedOut,
            double campaignPerformanceScore
    ) {
    }
}
