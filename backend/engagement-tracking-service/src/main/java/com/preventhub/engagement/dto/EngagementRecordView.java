package com.preventhub.engagement.dto;

import java.time.LocalDateTime;

public record EngagementRecordView(
        Long id,
        Long campaignId,
        Long citizenId,
        String citizenName,
        String region,
        String channel,
        boolean opened,
        boolean enrolled,
        boolean participated,
        boolean droppedOut,
        double engagementScore,
        LocalDateTime eventTimestamp
) {
}
