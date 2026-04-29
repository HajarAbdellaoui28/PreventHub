package com.preventhub.communication.service;

import com.preventhub.communication.domain.CommunicationLog;
import com.preventhub.communication.domain.MessageTemplate;
import com.preventhub.communication.dto.CommunicationDispatchResponse;
import com.preventhub.communication.dto.CommunicationHistoryView;
import com.preventhub.communication.dto.MessageTemplateView;
import com.preventhub.communication.dto.SendCommunicationRequest;
import com.preventhub.communication.repository.CommunicationLogRepository;
import com.preventhub.communication.repository.MessageTemplateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunicationOrchestrator {

    private final MessageTemplateRepository templateRepository;
    private final CommunicationLogRepository logRepository;

    public CommunicationOrchestrator(MessageTemplateRepository templateRepository,
                                     CommunicationLogRepository logRepository) {
        this.templateRepository = templateRepository;
        this.logRepository = logRepository;
    }

    public List<MessageTemplateView> templates() {
        return templateRepository.findAll().stream()
                .map(template -> new MessageTemplateView(
                        template.getId(),
                        template.getTitle(),
                        template.getCategory(),
                        template.getChannel(),
                        template.getMedicalValidationLevel(),
                        template.getBody()))
                .toList();
    }

    public CommunicationDispatchResponse send(SendCommunicationRequest request) {
        request.channels().forEach(channel -> logRepository.save(CommunicationLog.builder()
                .campaignId(request.campaignId())
                .subject(request.subject())
                .channel(channel)
                .recipientCount(request.citizenIds().size())
                .status("SIMULATED_SENT")
                .sentAt(LocalDateTime.now())
                .build()));
        return new CommunicationDispatchResponse(
                request.campaignId(),
                request.citizenIds().size(),
                request.channels().size(),
                "SIMULATED_SENT"
        );
    }

    public List<CommunicationHistoryView> history() {
        return logRepository.findAll().stream()
                .map(log -> new CommunicationHistoryView(
                        log.getId(),
                        log.getCampaignId(),
                        log.getSubject(),
                        log.getChannel(),
                        log.getRecipientCount(),
                        log.getStatus(),
                        log.getSentAt()))
                .toList();
    }
}
