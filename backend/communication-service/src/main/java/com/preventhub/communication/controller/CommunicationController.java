package com.preventhub.communication.controller;

import com.preventhub.communication.dto.CommunicationDispatchResponse;
import com.preventhub.communication.dto.CommunicationHistoryView;
import com.preventhub.communication.dto.MessageTemplateView;
import com.preventhub.communication.dto.SendCommunicationRequest;
import com.preventhub.communication.service.CommunicationOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/communications")
@Tag(name = "Communication")
public class CommunicationController {

    private final CommunicationOrchestrator orchestrator;

    public CommunicationController(CommunicationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @GetMapping("/templates")
    public List<MessageTemplateView> templates() {
        return orchestrator.templates();
    }

    @PostMapping("/send")
    public CommunicationDispatchResponse send(@Valid @RequestBody SendCommunicationRequest request) {
        return orchestrator.send(request);
    }

    @GetMapping("/history")
    public List<CommunicationHistoryView> history() {
        return orchestrator.history();
    }
}
