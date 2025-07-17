package com.itm.space.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.model.enums.ApplicationStatusName;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.itm.space.constant.ErrorMessagesConstant.ENTITY_NOT_FOUND_MESSAGE;
import static com.itm.space.model.enums.ApplicationStatusName.CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ApplicationStatusRepositoryIT extends BaseIntegrationTest {

    @Autowired
    private ApplicationStatusRepository applicationStatusRepository;

    @Test
    @DisplayName("Тест создания статуса и получения его по id")
    @DataSet(cleanBefore = true, executeStatementsBefore = "SELECT SETVAL('application_status_id_seq',1,false);")
    @ExpectedDataSet("datasets/repository/create/02-expectedApplicationStatus.yml")
    void createAndReturnEntity() {
        ApplicationStatus entity = ApplicationStatus.builder()
                .name(CREATED)
                .description("description").build();

        ApplicationStatus savedEntity = applicationStatusRepository.save(entity);

        Optional<ApplicationStatus> optional = applicationStatusRepository.findById(savedEntity.getId());

        ApplicationStatus found = assertDoesNotThrow(
                () -> optional.orElseThrow(
                        () -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE)
                ));

        assertThat(found.getId())
                .isEqualTo(savedEntity.getId());
        assertThat(found.getName())
                .isEqualTo(entity.getName());
        assertThat(found.getDescription())
                .isEqualTo(entity.getDescription());
    }

    @Test
    @DisplayName("Тест обновления статуса и получения его по id")
    @DataSet(cleanBefore = true, value = "datasets/repository/update/01-ApplicationStatus.yml")
    @ExpectedDataSet("datasets/repository/update/02-expectedApplicationStatus.yml")
    void updateAndReturnEntity() {
        Optional<ApplicationStatus> optional = applicationStatusRepository.findById(1);

        ApplicationStatus entity = assertDoesNotThrow(
                () -> optional.orElseThrow(
                        () -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE)
                ));

        assertThat(entity.getName())
                .isEqualTo(CREATED);
        assertThat(entity.getDescription())
                .isEqualTo("descriptionToUpdate");

        entity.setName(ApplicationStatusName.PENDING);
        entity.setDescription("descriptionUpdated");

        ApplicationStatus updatedEntity = applicationStatusRepository.save(entity);

        ApplicationStatus found = applicationStatusRepository.findById(updatedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE));

        assertThat(found.getId())
                .isEqualTo(entity.getId());
        assertThat(found.getName())
                .isEqualTo(entity.getName());
        assertThat(found.getDescription())
                .isEqualTo(entity.getDescription());
    }

}