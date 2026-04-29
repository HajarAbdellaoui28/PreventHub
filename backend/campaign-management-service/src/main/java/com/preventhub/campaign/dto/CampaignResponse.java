package com.preventhub.campaign.dto;

import com.preventhub.campaign.domain.CampaignStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CampaignResponse(
        Long id,
        String title,
        String objective,
        String campaignType,
        CampaignStatus status,
        Integer minAge,
        Integer maxAge,
        String gender,
        List<String> targetCities,
        List<String> targetRegions,
        List<String> targetConditions,
        LocalDate scheduledDate,
        Integer expectedPopulation,
        Integer currentAudienceSize,
        String ownerOrganization,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
