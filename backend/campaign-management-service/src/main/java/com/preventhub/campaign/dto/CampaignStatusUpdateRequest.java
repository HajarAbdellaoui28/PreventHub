package com.preventhub.campaign.dto;

import com.preventhub.campaign.domain.CampaignStatus;
import jakarta.validation.constraints.NotNull;

public record CampaignStatusUpdateRequest(@NotNull CampaignStatus status) {
}
