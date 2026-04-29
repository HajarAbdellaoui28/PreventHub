package com.preventhub.engagement.controller;

import com.preventhub.engagement.dto.CampaignEngagementSummary;
import com.preventhub.engagement.dto.EngagementRecordRequest;
import com.preventhub.engagement.dto.EngagementRecordView;
import com.preventhub.engagement.dto.EngagementStatsResponse;
import com.preventhub.engagement.service.EngagementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/engagement")
@Tag(name = "Engagement Tracking")
public class EngagementController {

    private final EngagementService engagementService;

    public EngagementController(EngagementService engagementService) {
        this.engagementService = engagementService;
    }

    @GetMapping("/records")
    public List<EngagementRecordView> records() {
        return engagementService.findAll();
    }

    @PostMapping("/records")
    public EngagementRecordView create(@Valid @RequestBody EngagementRecordRequest request) {
        return engagementService.create(request);
    }

    @GetMapping("/stats")
    public EngagementStatsResponse stats() {
        return engagementService.stats();
    }

    @GetMapping("/campaigns/{campaignId}/summary")
    public CampaignEngagementSummary campaignSummary(@PathVariable Long campaignId) {
        return engagementService.campaignSummary(campaignId);
    }
}
