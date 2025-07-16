package com.itm.space.repository;

import com.itm.space.domain.entity.ApplicationStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationStatusLogRepository extends JpaRepository<ApplicationStatusLog, UUID> {
}
