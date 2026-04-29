package com.preventhub.communication.dto;

public record MessageTemplateView(
        Long id,
        String title,
        String category,
        String channel,
        String medicalValidationLevel,
        String body
) {
}
