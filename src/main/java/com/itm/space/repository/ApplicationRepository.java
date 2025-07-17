package com.itm.space.repository;

import com.itm.space.domain.entity.Application;
import com.itm.space.model.enums.ApplicationStatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    long countByUserIdAndApplicationStatus_NameIn(UUID userId, List<ApplicationStatusName> applicationStatusNames);
}
