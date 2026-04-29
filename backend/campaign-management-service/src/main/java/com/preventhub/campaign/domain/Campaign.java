package com.preventhub.campaign.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2500)
    private String objective;

    @Column(nullable = false)
    private String campaignType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CampaignStatus status;

    private Integer minAge;
    private Integer maxAge;
    private String gender;

    @ElementCollection
    @CollectionTable(name = "campaign_cities", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "city")
    @Builder.Default
    private List<String> targetCities = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "campaign_regions", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "region")
    @Builder.Default
    private List<String> targetRegions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "campaign_conditions", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "condition_name")
    @Builder.Default
    private List<String> targetConditions = new ArrayList<>();

    private LocalDate scheduledDate;
    private Integer expectedPopulation;
    private Integer currentAudienceSize;
    private String ownerOrganization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
