package com.preventhub.risk.dto;

import java.util.List;

public record SegmentSearchRequest(
        Integer minAge,
        Integer maxAge,
        String gender,
        List<String> targetCities,
        List<String> targetRegions,
        List<String> targetConditions
) {
}
