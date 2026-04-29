package com.preventhub.risk.controller;

import com.preventhub.risk.dto.CitizenRiskView;
import com.preventhub.risk.dto.RiskEvaluationRequest;
import com.preventhub.risk.dto.RiskEvaluationResult;
import com.preventhub.risk.dto.RiskOverviewResponse;
import com.preventhub.risk.dto.SegmentSearchRequest;
import com.preventhub.risk.dto.SegmentSearchResponse;
import com.preventhub.risk.service.RiskScoringService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
@Tag(name = "Risk Stratification")
public class RiskController {

    private final RiskScoringService riskScoringService;

    public RiskController(RiskScoringService riskScoringService) {
        this.riskScoringService = riskScoringService;
    }

    @GetMapping("/citizens")
    public List<CitizenRiskView> citizens() {
        return riskScoringService.citizens();
    }

    @PostMapping("/evaluate")
    public RiskEvaluationResult evaluate(@Valid @RequestBody RiskEvaluationRequest request) {
        return riskScoringService.evaluate(request.citizenId(), request.campaignConditions());
    }

    @PostMapping("/segments/search")
    public SegmentSearchResponse search(@RequestBody SegmentSearchRequest request) {
        return riskScoringService.search(request);
    }

    @GetMapping("/overview")
    public RiskOverviewResponse overview() {
        return riskScoringService.overview();
    }
}
