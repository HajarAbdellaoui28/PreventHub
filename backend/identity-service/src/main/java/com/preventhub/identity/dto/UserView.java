package com.preventhub.identity.dto;

import java.util.List;

public record UserView(
        Long id,
        String fullName,
        String email,
        String organization,
        List<String> roles
) {
}
