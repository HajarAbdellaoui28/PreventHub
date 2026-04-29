package com.preventhub.campaign.repository;

import com.preventhub.campaign.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
}
