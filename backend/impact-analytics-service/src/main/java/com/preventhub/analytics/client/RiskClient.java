package com.preventhub.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "risk-stratification-service")
public interface RiskClient {

    @GetMapping("/api/risk/overview")
    RiskOverview overview();

    record RiskOverview(
            long totalCitizens,
            long highRisk,
            long mediumRisk,
            long lowRisk,
            String topRiskRegion,
            String topRiskCondition
    ) {
    }
}
