package com.preventhub.engagement.repository;

import com.preventhub.engagement.domain.EngagementRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementRecordRepository extends JpaRepository<EngagementRecord, Long> {

    List<EngagementRecord> findByCampaignId(Long campaignId);
}
