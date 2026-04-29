package com.preventhub.analytics.service;

import com.preventhub.analytics.client.CampaignClient;
import com.preventhub.analytics.client.EngagementClient;
import com.preventhub.analytics.client.RiskClient;
import com.preventhub.analytics.dto.CampaignAnalyticsResponse;
import com.preventhub.analytics.dto.DashboardAnalyticsResponse;
import com.preventhub.analytics.dto.GlobalKpiResponse;
import com.preventhub.analytics.repository.AnalyticsInsightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final CampaignClient campaignClient;
    private final EngagementClient engagementClient;
    private final RiskClient riskClient;
    private final AnalyticsInsightRepository insightRepository;

    public AnalyticsService(CampaignClient campaignClient,
                            EngagementClient engagementClient,
                            RiskClient riskClient,
                            AnalyticsInsightRepository insightRepository) {
        this.campaignClient = campaignClient;
        this.engagementClient = engagementClient;
        this.riskClient = riskClient;
        this.insightRepository = insightRepository;
    }

    public DashboardAnalyticsResponse dashboard() {
        List<CampaignClient.CampaignView> campaigns = campaignClient.campaigns();
        EngagementClient.EngagementStats engagementStats = engagementClient.stats();
        RiskClient.RiskOverview riskOverview = riskClient.overview();
        GlobalKpiResponse kpis = buildKpis(campaigns, engagementStats, riskOverview);

        return new DashboardAnalyticsResponse(
                kpis,
                campaigns.stream()
                        .filter(campaign -> "ACTIVE".equalsIgnoreCase(campaign.status()) || "SCHEDULED".equalsIgnoreCase(campaign.status()))
                        .map(campaign -> new DashboardAnalyticsResponse.CampaignKpiView(
                                campaign.id(),
                                campaign.title(),
                                campaign.status(),
                                campaign.currentAudienceSize() == null ? 0 : campaign.currentAudienceSize()))
                        .toList(),
                new DashboardAnalyticsResponse.RiskDistributionView(
                        riskOverview.highRisk(),
                        riskOverview.mediumRisk(),
                        riskOverview.lowRisk(),
                        riskOverview.topRiskRegion()
                ),
                insightRepository.findAll().stream().map(insight -> insight.getTitle() + ": " + insight.getNarrative()).toList()
        );
    }

    public CampaignAnalyticsResponse campaign(Long campaignId) {
        CampaignClient.CampaignView campaign = campaignClient.campaign(campaignId);
        EngagementClient.CampaignSummary summary = engagementClient.campaignSummary(campaignId);
        double effectiveness = Math.round((summary.campaignPerformanceScore() * 0.6
                + ratio(summary.participated(), Math.max(summary.totalTargets(), 1)) * 40.0) * 100.0) / 100.0;
        double savings = Math.round(summary.participated() * 185.0 * 100.0) / 100.0;
        return new CampaignAnalyticsResponse(
                campaignId,
                campaign.title(),
                summary.totalTargets(),
                summary.opened(),
                summary.participated(),
                summary.droppedOut(),
                effectiveness,
                savings
        );
    }

    public GlobalKpiResponse kpis() {
        return buildKpis(campaignClient.campaigns(), engagementClient.stats(), riskClient.overview());
    }

    private GlobalKpiResponse buildKpis(List<CampaignClient.CampaignView> campaigns,
                                        EngagementClient.EngagementStats stats,
                                        RiskClient.RiskOverview risk) {
        double coverageRate = ratio(
                campaigns.stream().map(CampaignClient.CampaignView::currentAudienceSize).filter(java.util.Objects::nonNull).mapToInt(Integer::intValue).sum(),
                Math.max(risk.totalCitizens(), 1)
        );
        double estimatedRoi = Math.round((stats.participatedCount() * 185.0 - campaigns.size() * 2200.0) / Math.max(campaigns.size(), 1) * 100.0) / 100.0;
        return new GlobalKpiResponse(
                campaigns.size(),
                round(stats.openRate() * 100),
                round(stats.participationRate() * 100),
                round(coverageRate * 100),
                estimatedRoi
        );
    }

    private double ratio(long numerator, long denominator) {
        return denominator == 0 ? 0 : (double) numerator / denominator;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
