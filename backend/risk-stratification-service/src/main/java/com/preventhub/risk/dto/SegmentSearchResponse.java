package com.preventhub.risk.dto;

import java.util.List;

public record SegmentSearchResponse(
        List<CitizenRiskView> citizens,
        long total,
        long highRiskCount,
        long mediumRiskCount,
        long lowRiskCount
) {
}
