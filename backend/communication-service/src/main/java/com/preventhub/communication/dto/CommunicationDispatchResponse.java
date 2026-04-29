package com.preventhub.communication.dto;

public record CommunicationDispatchResponse(
        Long campaignId,
        int totalRecipients,
        int createdJobs,
        String status
) {
}
