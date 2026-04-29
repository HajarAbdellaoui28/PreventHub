package com.preventhub.engagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "engagement_records")
public class EngagementRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long campaignId;
    private Long citizenId;
    private String citizenName;
    private String region;
    private String channel;
    private boolean opened;
    private boolean enrolled;
    private boolean participated;
    private boolean droppedOut;
    private double engagementScore;
    private LocalDateTime eventTimestamp;
}
