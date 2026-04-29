package com.preventhub.communication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SendCommunicationRequest(
        @NotNull Long campaignId,
        @NotBlank String subject,
        @NotBlank String messageBody,
        @NotEmpty List<Long> citizenIds,
        @NotEmpty List<String> channels
) {
}
