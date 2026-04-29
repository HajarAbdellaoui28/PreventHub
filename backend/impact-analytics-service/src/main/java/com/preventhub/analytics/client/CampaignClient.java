package com.preventhub.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "campaign-management-service")
public interface CampaignClient {

    @GetMapping("/api/campaigns")
    List<CampaignView> campaigns();

    @GetMapping("/api/campaigns/{id}")
    CampaignView campaign(@PathVariable("id") Long id);

    record CampaignView(
            Long id,
            String title,
            String status,
            Integer currentAudienceSize
    ) {
    }
}
