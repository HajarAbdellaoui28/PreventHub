package com.preventhub.communication.repository;

import com.preventhub.communication.domain.CommunicationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationLogRepository extends JpaRepository<CommunicationLog, Long> {
}
