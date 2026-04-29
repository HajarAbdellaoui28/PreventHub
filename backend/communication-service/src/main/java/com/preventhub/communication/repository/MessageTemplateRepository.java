package com.preventhub.communication.repository;

import com.preventhub.communication.domain.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
}
