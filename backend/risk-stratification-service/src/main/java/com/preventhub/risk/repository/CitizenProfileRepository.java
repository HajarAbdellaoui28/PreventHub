package com.preventhub.risk.repository;

import com.preventhub.risk.domain.CitizenProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitizenProfileRepository extends JpaRepository<CitizenProfile, Long> {
}
