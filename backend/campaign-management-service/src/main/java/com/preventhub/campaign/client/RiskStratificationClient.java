package com.preventhub.campaign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "risk-stratification-service")
public interface RiskStratificationClient {

    @PostMapping("/api/risk/segments/search")
    SegmentSearchResponse search(@RequestBody SegmentSearchRequest request);

    record SegmentSearchRequest(
            Integer minAge,
            Integer maxAge,
            String gender,
            List<String> targetCities,
            List<String> targetRegions,
            List<String> targetConditions
    ) {
    }

    record CitizenRiskView(
            Long citizenId,
            String fullName,
            String city,
            String region,
            String riskLevel
    ) {
    }

    record SegmentSearchResponse(
            List<CitizenRiskView> citizens,
            long total,
            long highRiskCount,
            long mediumRiskCount,
            long lowRiskCount
    ) {
    }
}
