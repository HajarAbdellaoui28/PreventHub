package com.preventhub.analytics.controller;

import com.preventhub.analytics.dto.CampaignAnalyticsResponse;
import com.preventhub.analytics.dto.DashboardAnalyticsResponse;
import com.preventhub.analytics.dto.GlobalKpiResponse;
import com.preventhub.analytics.service.AnalyticsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "Impact Analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/dashboard")
    public DashboardAnalyticsResponse dashboard() {
        return analyticsService.dashboard();
    }

    @GetMapping("/campaigns/{campaignId}")
    public CampaignAnalyticsResponse campaign(@PathVariable Long campaignId) {
        return analyticsService.campaign(campaignId);
    }

    @GetMapping("/kpis")
    public GlobalKpiResponse kpis() {
        return analyticsService.kpis();
    }
}
