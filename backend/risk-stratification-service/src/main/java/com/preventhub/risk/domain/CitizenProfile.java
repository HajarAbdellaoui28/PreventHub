package com.preventhub.risk.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "citizen_profiles")
public class CitizenProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nationalId;
    private String fullName;
    private Integer age;
    private String gender;
    private String city;
    private String region;
    private String socioEconomicBand;
    private Boolean smoker;
    private Boolean diabetic;
    private Boolean hypertensive;
    private Boolean sedentary;
    private Double bmi;
    private LocalDate lastScreeningDate;

    @ElementCollection
    @CollectionTable(name = "citizen_conditions", joinColumns = @JoinColumn(name = "citizen_id"))
    @Column(name = "condition_name")
    @Builder.Default
    private List<String> chronicConditions = new ArrayList<>();
}
