package com.preventhub.engagement.service;

import com.preventhub.engagement.domain.EngagementRecord;
import com.preventhub.engagement.dto.CampaignEngagementSummary;
import com.preventhub.engagement.dto.EngagementRecordRequest;
import com.preventhub.engagement.dto.EngagementRecordView;
import com.preventhub.engagement.dto.EngagementStatsResponse;
import com.preventhub.engagement.repository.EngagementRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EngagementService {

    private final EngagementRecordRepository repository;

    public EngagementService(EngagementRecordRepository repository) {
        this.repository = repository;
    }

    public List<EngagementRecordView> findAll() {
        return repository.findAll().stream().map(this::toView).toList();
    }

    public EngagementRecordView create(EngagementRecordRequest request) {
        EngagementRecord record = repository.save(EngagementRecord.builder()
                .campaignId(request.campaignId())
                .citizenId(request.citizenId())
                .citizenName(request.citizenName())
                .region(request.region())
                .channel(request.channel())
                .opened(request.opened())
                .enrolled(request.enrolled())
                .participated(request.participated())
                .droppedOut(request.droppedOut())
                .engagementScore(score(request))
                .eventTimestamp(LocalDateTime.now())
                .build());
        return toView(record);
    }

    public EngagementStatsResponse stats() {
        return compute(repository.findAll());
    }

    public CampaignEngagementSummary campaignSummary(Long campaignId) {
        List<EngagementRecord> records = repository.findByCampaignId(campaignId);
        EngagementStatsResponse stats = compute(records);
        return new CampaignEngagementSummary(
                campaignId,
                records.size(),
                stats.openedCount(),
                stats.enrolledCount(),
                stats.participatedCount(),
                stats.droppedOutCount(),
                Math.round((stats.averageEngagementScore() + stats.participationRate() * 100) / 2.0 * 100.0) / 100.0
        );
    }

    private EngagementStatsResponse compute(List<EngagementRecord> records) {
        long total = records.size();
        long opened = records.stream().filter(EngagementRecord::isOpened).count();
        long enrolled = records.stream().filter(EngagementRecord::isEnrolled).count();
        long participated = records.stream().filter(EngagementRecord::isParticipated).count();
        long dropped = records.stream().filter(EngagementRecord::isDroppedOut).count();
        double averageScore = total == 0 ? 0 : records.stream().mapToDouble(EngagementRecord::getEngagementScore).average().orElse(0);
        return new EngagementStatsResponse(
                total,
                opened,
                enrolled,
                participated,
                dropped,
                ratio(opened, total),
                ratio(participated, total),
                ratio(dropped, total),
                Math.round(averageScore * 100.0) / 100.0
        );
    }

    private double score(EngagementRecordRequest request) {
        double score = 0;
        if (request.opened()) {
            score += 25;
        }
        if (request.enrolled()) {
            score += 25;
        }
        if (request.participated()) {
            score += 40;
        }
        if (request.droppedOut()) {
            score -= 20;
        }
        return Math.max(score, 0);
    }

    private double ratio(long numerator, long denominator) {
        return denominator == 0 ? 0 : (double) numerator / denominator;
    }

    private EngagementRecordView toView(EngagementRecord record) {
        return new EngagementRecordView(
                record.getId(),
                record.getCampaignId(),
                record.getCitizenId(),
                record.getCitizenName(),
                record.getRegion(),
                record.getChannel(),
                record.isOpened(),
                record.isEnrolled(),
                record.isParticipated(),
                record.isDroppedOut(),
                record.getEngagementScore(),
                record.getEventTimestamp()
        );
    }
}
