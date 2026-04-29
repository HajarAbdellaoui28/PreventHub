package com.preventhub.campaign.repository;

import com.preventhub.campaign.domain.CampaignHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignHistoryRepository extends JpaRepository<CampaignHistory, Long> {

    List<CampaignHistory> findByCampaignIdOrderByOccurredAtDesc(Long campaignId);
}
