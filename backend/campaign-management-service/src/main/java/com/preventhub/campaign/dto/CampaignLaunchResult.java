package com.preventhub.campaign.dto;

import java.util.List;

public record CampaignLaunchResult(
        Long campaignId,
        int audienceSize,
        int communicationJobs,
        List<String> channelsTriggered,
        String summary
) {
}
