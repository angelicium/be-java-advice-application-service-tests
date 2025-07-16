package com.itm.space.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.domain.entity.ApplicationStatusLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ApplicationStatusLogRepositoryIT extends BaseIntegrationTest {

    @Autowired
    private ApplicationStatusLogRepository applicationStatusLogRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Test
    @DisplayName("Создание application_status_log в БД - успешно")
    @DataSet(
            value = "datasets/ApplicationStatusLogRepository/save/01-log.yaml",
            cleanBefore = true,
            cleanAfter = true
    )
    @ExpectedDataSet(
            value = "datasets/ApplicationStatusLogRepository/save/01-logExpected.yaml",
            ignoreCols = {"changed_at"}
    )
    void createLogSuccess() {
        UUID id = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");
        UUID applicationId = UUID.fromString("11111111-2222-3333-4444-555555555555");
        Integer statusId = 2;
        UUID changedBy = UUID.fromString("99999999-8888-7777-6666-555555555555");

        Application application = applicationRepository.findById(applicationId).orElseThrow();
        ApplicationStatus status = applicationStatusRepository.findById(statusId).orElseThrow();

        ApplicationStatusLog log = new ApplicationStatusLog();
        log.setId(id);
        log.setApplication(application);
        log.setApplicationStatus(status);
        log.setChangedBy(changedBy);
        log.setChangedAt(LocalDateTime.now());

        assertDoesNotThrow(() -> applicationStatusLogRepository.save(log));
    }

    @Test
    @DisplayName("Обновление application_status_log в БД - успешно")
    @DataSet(
            value = "datasets/ApplicationStatusLogRepository/update/01-log.yaml",
            cleanBefore = true,
            cleanAfter = true
    )
    @ExpectedDataSet(
            value = "datasets/ApplicationStatusLogRepository/update/01-logExpected.yaml",
            ignoreCols = {"changed_at"}
    )
    void updateLogSuccess() {
        UUID existingId = UUID.fromString("d290f1ee-6c54-4b01-90e6-d701748f0851");
        Integer statusId = 3;
        UUID changedBy = UUID.fromString("12341234-5678-90ab-cdef-1234567890ab");

        ApplicationStatusLog existing = applicationStatusLogRepository.findById(existingId).orElseThrow();
        ApplicationStatus status = applicationStatusRepository.findById(statusId).orElseThrow();

        existing.setApplicationStatus(status);
        existing.setChangedBy(changedBy);
        existing.setChangedAt(LocalDateTime.now());

        applicationStatusLogRepository.save(existing);
    }
}