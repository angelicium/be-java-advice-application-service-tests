package com.itm.space.repository;

import com.itm.space.domain.entity.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Integer> {
}
