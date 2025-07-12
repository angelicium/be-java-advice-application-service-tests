package com.itm.space.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationRepositoryIT extends BaseIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    @DisplayName("Тест на создание сущности Application")
    @DataSet(value = "datasets/repository/update/01-ApplicationStatus.yml", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "datasets/repository/create/03-expectedApplication.yml", ignoreCols = {"id", "created_at", "updated_at"})
    public void shouldCreateAndFindApplication() throws Exception {
        Application application = jsonParserUtil.getObjectFromJson(
                "json/repository/01-application.json",
                Application.class
        );

        ApplicationStatus status = entityManager.getReference(ApplicationStatus.class, 1);
        application.setApplicationStatus(status);

        applicationRepository.save(application);
    }

    @Test
    @DisplayName("Тест на обновление сущности Application")
    @DataSet(value = "datasets/repository/update/01-ApplicationStatus.yml", cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet(value = "datasets/repository/update/03-expectedApplication.yml", ignoreCols = {"id", "created_at", "updated_at"})
    public void shouldUpdateApplication() throws Exception {
        Application application = jsonParserUtil.getObjectFromJson(
                "json/repository/01-application.json",
                Application.class
        );

        ApplicationStatus status = entityManager.getReference(ApplicationStatus.class, 1);
        application.setApplicationStatus(status);

        Application saved = applicationRepository.save(application);
        saved.setSkills("Java");
        applicationRepository.save(saved);
    }
}
