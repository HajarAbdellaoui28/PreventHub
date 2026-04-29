package com.preventhub.campaign.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CampaignRequest(
        @NotBlank String title,
        @NotBlank String objective,
        @NotBlank String campaignType,
        @NotNull Integer minAge,
        @NotNull Integer maxAge,
        String gender,
        @NotEmpty List<String> targetCities,
        @NotEmpty List<String> targetRegions,
        @NotEmpty List<String> targetConditions,
        LocalDate scheduledDate,
        Integer expectedPopulation,
        @NotBlank String ownerOrganization
) {
}
