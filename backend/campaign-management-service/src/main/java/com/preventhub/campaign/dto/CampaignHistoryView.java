package com.preventhub.campaign.dto;

import java.time.LocalDateTime;

public record CampaignHistoryView(
        Long id,
        String actionType,
        String actor,
        String description,
        LocalDateTime occurredAt
) {
}
