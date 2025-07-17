package com.itm.space.repository;

import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.model.enums.ApplicationStatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Integer> {
    ApplicationStatus findByName(ApplicationStatusName name);
}
