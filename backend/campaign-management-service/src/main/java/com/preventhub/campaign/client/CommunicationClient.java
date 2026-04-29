package com.preventhub.campaign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "communication-service")
public interface CommunicationClient {

    @PostMapping("/api/communications/send")
    CommunicationDispatchResponse send(@RequestBody CommunicationDispatchRequest request);

    record CommunicationDispatchRequest(
            Long campaignId,
            String subject,
            String messageBody,
            List<Long> citizenIds,
            List<String> channels
    ) {
    }

    record CommunicationDispatchResponse(
            Long campaignId,
            int totalRecipients,
            int createdJobs,
            String status
    ) {
    }
}
