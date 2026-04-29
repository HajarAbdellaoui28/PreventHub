package com.preventhub.engagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EngagementRecordRequest(
        @NotNull Long campaignId,
        @NotNull Long citizenId,
        @NotBlank String citizenName,
        @NotBlank String region,
        @NotBlank String channel,
        boolean opened,
        boolean enrolled,
        boolean participated,
        boolean droppedOut
) {
}
