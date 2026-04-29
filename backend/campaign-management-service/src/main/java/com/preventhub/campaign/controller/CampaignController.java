package com.preventhub.campaign.controller;

import com.preventhub.campaign.dto.CampaignHistoryView;
import com.preventhub.campaign.dto.CampaignLaunchResult;
import com.preventhub.campaign.dto.CampaignRequest;
import com.preventhub.campaign.dto.CampaignResponse;
import com.preventhub.campaign.dto.CampaignStatusUpdateRequest;
import com.preventhub.campaign.service.CampaignService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@Tag(name = "Campaign Management")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<CampaignResponse> findAll() {
        return campaignService.findAll();
    }

    @GetMapping("/{id}")
    public CampaignResponse findById(@PathVariable Long id) {
        return campaignService.findById(id);
    }

    @PostMapping
    public CampaignResponse create(@Valid @RequestBody CampaignRequest request,
                                   @RequestHeader(value = "X-Auth-User", defaultValue = "system") String actor) {
        return campaignService.create(request, actor);
    }

    @PutMapping("/{id}")
    public CampaignResponse update(@PathVariable Long id,
                                   @Valid @RequestBody CampaignRequest request,
                                   @RequestHeader(value = "X-Auth-User", defaultValue = "system") String actor) {
        return campaignService.update(id, request, actor);
    }

    @PatchMapping("/{id}/status")
    public CampaignResponse updateStatus(@PathVariable Long id,
                                         @Valid @RequestBody CampaignStatusUpdateRequest request,
                                         @RequestHeader(value = "X-Auth-User", defaultValue = "system") String actor) {
        return campaignService.updateStatus(id, request.status(), actor);
    }

    @PostMapping("/{id}/schedule")
    public CampaignResponse schedule(@PathVariable Long id,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduledDate,
                                     @RequestHeader(value = "X-Auth-User", defaultValue = "system") String actor) {
        return campaignService.schedule(id, scheduledDate, actor);
    }

    @PostMapping("/{id}/launch")
    public CampaignLaunchResult launch(@PathVariable Long id,
                                       @RequestHeader(value = "X-Auth-User", defaultValue = "system") String actor) {
        return campaignService.launch(id, actor);
    }

    @GetMapping("/{id}/history")
    public List<CampaignHistoryView> history(@PathVariable Long id) {
        return campaignService.history(id);
    }
}
