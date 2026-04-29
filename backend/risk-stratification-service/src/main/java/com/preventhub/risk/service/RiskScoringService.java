package com.preventhub.risk.service;

import com.preventhub.risk.domain.CitizenProfile;
import com.preventhub.risk.domain.RiskLevel;
import com.preventhub.risk.dto.CitizenRiskView;
import com.preventhub.risk.dto.RiskEvaluationResult;
import com.preventhub.risk.dto.RiskOverviewResponse;
import com.preventhub.risk.dto.SegmentSearchRequest;
import com.preventhub.risk.dto.SegmentSearchResponse;
import com.preventhub.risk.repository.CitizenProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RiskScoringService {

    private final CitizenProfileRepository repository;

    public RiskScoringService(CitizenProfileRepository repository) {
        this.repository = repository;
    }

    public List<CitizenRiskView> citizens() {
        return repository.findAll().stream().map(this::toView).toList();
    }

    public RiskEvaluationResult evaluate(Long citizenId, List<String> campaignConditions) {
        CitizenProfile citizen = repository.findById(citizenId)
                .orElseThrow(() -> new IllegalArgumentException("Citizen " + citizenId + " not found"));
        Score score = score(citizen, campaignConditions);
        return new RiskEvaluationResult(citizen.getId(), citizen.getFullName(), score.level(), score.score(), score.factors());
    }

    public SegmentSearchResponse search(SegmentSearchRequest request) {
        List<CitizenRiskView> matches = repository.findAll().stream()
                .filter(ageFilter(request))
                .filter(genderFilter(request))
                .filter(listFilter(request.targetCities(), CitizenProfile::getCity))
                .filter(listFilter(request.targetRegions(), CitizenProfile::getRegion))
                .filter(conditionFilter(request.targetConditions()))
                .map(this::toView)
                .sorted(Comparator.comparingInt(CitizenRiskView::riskScore).reversed())
                .toList();

        return new SegmentSearchResponse(
                matches,
                matches.size(),
                matches.stream().filter(item -> item.riskLevel() == RiskLevel.HIGH).count(),
                matches.stream().filter(item -> item.riskLevel() == RiskLevel.MEDIUM).count(),
                matches.stream().filter(item -> item.riskLevel() == RiskLevel.LOW).count()
        );
    }

    public RiskOverviewResponse overview() {
        List<CitizenRiskView> citizens = citizens();
        Map<String, Long> highRiskByRegion = citizens.stream()
                .filter(item -> item.riskLevel() == RiskLevel.HIGH)
                .collect(Collectors.groupingBy(CitizenRiskView::region, Collectors.counting()));
        Map<String, Long> conditions = repository.findAll().stream()
                .flatMap(citizen -> citizen.getChronicConditions().stream())
                .collect(Collectors.groupingBy(condition -> condition, Collectors.counting()));

        return new RiskOverviewResponse(
                citizens.size(),
                citizens.stream().filter(item -> item.riskLevel() == RiskLevel.HIGH).count(),
                citizens.stream().filter(item -> item.riskLevel() == RiskLevel.MEDIUM).count(),
                citizens.stream().filter(item -> item.riskLevel() == RiskLevel.LOW).count(),
                highRiskByRegion.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A"),
                conditions.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A")
        );
    }

    private CitizenRiskView toView(CitizenProfile citizen) {
        Score score = score(citizen, citizen.getChronicConditions());
        return new CitizenRiskView(
                citizen.getId(),
                citizen.getFullName(),
                citizen.getAge(),
                citizen.getGender(),
                citizen.getCity(),
                citizen.getRegion(),
                score.level(),
                score.score(),
                citizen.getChronicConditions()
        );
    }

    private Score score(CitizenProfile citizen, List<String> campaignConditions) {
        int score = 0;
        List<String> factors = new ArrayList<>();

        if (citizen.getAge() >= 60) {
            score += 25;
            factors.add("advanced-age");
        } else if (citizen.getAge() >= 45) {
            score += 12;
            factors.add("mid-age");
        }
        if (Boolean.TRUE.equals(citizen.getDiabetic())) {
            score += 20;
            factors.add("diabetes");
        }
        if (Boolean.TRUE.equals(citizen.getHypertensive())) {
            score += 18;
            factors.add("hypertension");
        }
        if (Boolean.TRUE.equals(citizen.getSmoker())) {
            score += 12;
            factors.add("smoking");
        }
        if (Boolean.TRUE.equals(citizen.getSedentary())) {
            score += 10;
            factors.add("sedentary-lifestyle");
        }
        if (citizen.getBmi() != null && citizen.getBmi() >= 30) {
            score += 15;
            factors.add("obesity");
        }
        if (citizen.getLastScreeningDate() != null && citizen.getLastScreeningDate().isBefore(LocalDate.now().minusYears(2))) {
            score += 8;
            factors.add("screening-overdue");
        }
        if (citizen.getRegion() != null && List.of("Casablanca-Settat", "Fes-Meknes").contains(citizen.getRegion())) {
            score += 6;
            factors.add("high-incidence-region");
        }
        if (campaignConditions != null && citizen.getChronicConditions().stream().anyMatch(campaignConditions::contains)) {
            score += 10;
            factors.add("campaign-condition-match");
        }

        RiskLevel level = score >= 55 ? RiskLevel.HIGH : score >= 30 ? RiskLevel.MEDIUM : RiskLevel.LOW;
        return new Score(score, level, factors);
    }

    private Predicate<CitizenProfile> ageFilter(SegmentSearchRequest request) {
        return citizen -> (request.minAge() == null || citizen.getAge() >= request.minAge())
                && (request.maxAge() == null || citizen.getAge() <= request.maxAge());
    }

    private Predicate<CitizenProfile> genderFilter(SegmentSearchRequest request) {
        return citizen -> request.gender() == null || request.gender().equalsIgnoreCase("ALL")
                || request.gender().equalsIgnoreCase(citizen.getGender());
    }

    private Predicate<CitizenProfile> listFilter(List<String> accepted, java.util.function.Function<CitizenProfile, String> accessor) {
        return citizen -> accepted == null || accepted.isEmpty() || accepted.contains(accessor.apply(citizen));
    }

    private Predicate<CitizenProfile> conditionFilter(List<String> targetConditions) {
        return citizen -> targetConditions == null || targetConditions.isEmpty()
                || citizen.getChronicConditions().stream().anyMatch(targetConditions::contains);
    }

    private record Score(int score, RiskLevel level, List<String> factors) {
    }
}
