package com.preventhub.communication.dto;

import java.time.LocalDateTime;

public record CommunicationHistoryView(
        Long id,
        Long campaignId,
        String subject,
        String channel,
        int recipientCount,
        String status,
        LocalDateTime sentAt
) {
}
