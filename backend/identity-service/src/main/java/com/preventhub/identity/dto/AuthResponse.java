package com.preventhub.identity.dto;

import java.util.List;

public record AuthResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds,
        UserSummary user
) {
    public record UserSummary(
            Long id,
            String fullName,
            String email,
            String organization,
            List<String> roles
    ) {
    }
}
