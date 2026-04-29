package com.preventhub.campaign.service;

import com.preventhub.campaign.client.CommunicationClient;
import com.preventhub.campaign.client.RiskStratificationClient;
import com.preventhub.campaign.domain.Campaign;
import com.preventhub.campaign.domain.CampaignHistory;
import com.preventhub.campaign.domain.CampaignStatus;
import com.preventhub.campaign.dto.CampaignHistoryView;
import com.preventhub.campaign.dto.CampaignLaunchResult;
import com.preventhub.campaign.dto.CampaignRequest;
import com.preventhub.campaign.dto.CampaignResponse;
import com.preventhub.campaign.repository.CampaignHistoryRepository;
import com.preventhub.campaign.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignHistoryRepository historyRepository;
    private final RiskStratificationClient riskClient;
    private final CommunicationClient communicationClient;

    public CampaignService(CampaignRepository campaignRepository,
                           CampaignHistoryRepository historyRepository,
                           RiskStratificationClient riskClient,
                           CommunicationClient communicationClient) {
        this.campaignRepository = campaignRepository;
        this.historyRepository = historyRepository;
        this.riskClient = riskClient;
        this.communicationClient = communicationClient;
    }

    public List<CampaignResponse> findAll() {
        return campaignRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CampaignResponse findById(Long id) {
        return toResponse(load(id));
    }

    public CampaignResponse create(CampaignRequest request, String actor) {
        Campaign campaign = campaignRepository.save(Campaign.builder()
                .title(request.title())
                .objective(request.objective())
                .campaignType(request.campaignType())
                .status(request.scheduledDate() == null ? CampaignStatus.DRAFT : CampaignStatus.SCHEDULED)
                .minAge(request.minAge())
                .maxAge(request.maxAge())
                .gender(request.gender())
                .targetCities(request.targetCities())
                .targetRegions(request.targetRegions())
                .targetConditions(request.targetConditions())
                .scheduledDate(request.scheduledDate())
                .expectedPopulation(request.expectedPopulation())
                .currentAudienceSize(0)
                .ownerOrganization(request.ownerOrganization())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        addHistory(campaign.getId(), "CREATED", actor, "Campaign created with targeted criteria.");
        return toResponse(campaign);
    }

    public CampaignResponse update(Long id, CampaignRequest request, String actor) {
        Campaign campaign = load(id);
        campaign.setTitle(request.title());
        campaign.setObjective(request.objective());
        campaign.setCampaignType(request.campaignType());
        campaign.setMinAge(request.minAge());
        campaign.setMaxAge(request.maxAge());
        campaign.setGender(request.gender());
        campaign.setTargetCities(request.targetCities());
        campaign.setTargetRegions(request.targetRegions());
        campaign.setTargetConditions(request.targetConditions());
        campaign.setScheduledDate(request.scheduledDate());
        campaign.setExpectedPopulation(request.expectedPopulation());
        campaign.setOwnerOrganization(request.ownerOrganization());
        campaign.setUpdatedAt(LocalDateTime.now());
        Campaign saved = campaignRepository.save(campaign);
        addHistory(id, "UPDATED", actor, "Campaign content and targeting updated.");
        return toResponse(saved);
    }

    public CampaignResponse updateStatus(Long id, CampaignStatus status, String actor) {
        Campaign campaign = load(id);
        campaign.setStatus(status);
        campaign.setUpdatedAt(LocalDateTime.now());
        Campaign saved = campaignRepository.save(campaign);
        addHistory(id, "STATUS_CHANGED", actor, "Campaign status switched to " + status + ".");
        return toResponse(saved);
    }

    public CampaignResponse schedule(Long id, LocalDate scheduledDate, String actor) {
        Campaign campaign = load(id);
        campaign.setScheduledDate(scheduledDate);
        campaign.setStatus(CampaignStatus.SCHEDULED);
        campaign.setUpdatedAt(LocalDateTime.now());
        Campaign saved = campaignRepository.save(campaign);
        addHistory(id, "SCHEDULED", actor, "Campaign scheduled for " + scheduledDate + ".");
        return toResponse(saved);
    }

    public CampaignLaunchResult launch(Long id, String actor) {
        Campaign campaign = load(id);
        RiskStratificationClient.SegmentSearchResponse audience = riskClient.search(new RiskStratificationClient.SegmentSearchRequest(
                campaign.getMinAge(),
                campaign.getMaxAge(),
                campaign.getGender(),
                campaign.getTargetCities(),
                campaign.getTargetRegions(),
                campaign.getTargetConditions()
        ));

        CommunicationClient.CommunicationDispatchResponse dispatch = communicationClient.send(
                new CommunicationClient.CommunicationDispatchRequest(
                        id,
                        campaign.getTitle(),
                        campaign.getObjective(),
                        audience.citizens().stream().map(RiskStratificationClient.CitizenRiskView::citizenId).toList(),
                        List.of("EMAIL", "SMS", "PUSH")
                )
        );

        campaign.setStatus(CampaignStatus.ACTIVE);
        campaign.setCurrentAudienceSize((int) audience.total());
        campaign.setUpdatedAt(LocalDateTime.now());
        campaignRepository.save(campaign);
        addHistory(id, "LAUNCHED", actor, "Campaign launched on " + audience.total() + " citizens.");

        return new CampaignLaunchResult(
                id,
                (int) audience.total(),
                dispatch.createdJobs(),
                List.of("EMAIL", "SMS", "PUSH"),
                "Campaign successfully activated with risk-aware targeting."
        );
    }

    public List<CampaignHistoryView> history(Long campaignId) {
        return historyRepository.findByCampaignIdOrderByOccurredAtDesc(campaignId).stream()
                .map(item -> new CampaignHistoryView(item.getId(), item.getActionType(), item.getActor(), item.getDescription(), item.getOccurredAt()))
                .toList();
    }

    private Campaign load(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campaign " + id + " not found"));
    }

    private void addHistory(Long campaignId, String action, String actor, String description) {
        historyRepository.save(CampaignHistory.builder()
                .campaignId(campaignId)
                .actionType(action)
                .actor(actor)
                .description(description)
                .occurredAt(LocalDateTime.now())
                .build());
    }

    private CampaignResponse toResponse(Campaign campaign) {
        return new CampaignResponse(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getObjective(),
                campaign.getCampaignType(),
                campaign.getStatus(),
                campaign.getMinAge(),
                campaign.getMaxAge(),
                campaign.getGender(),
                campaign.getTargetCities(),
                campaign.getTargetRegions(),
                campaign.getTargetConditions(),
                campaign.getScheduledDate(),
                campaign.getExpectedPopulation(),
                campaign.getCurrentAudienceSize(),
                campaign.getOwnerOrganization(),
                campaign.getCreatedAt(),
                campaign.getUpdatedAt()
        );
    }
}
