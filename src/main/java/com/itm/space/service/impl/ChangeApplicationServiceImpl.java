package com.itm.space.service.impl;

import com.itm.space.domain.entity.Application;
import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import com.itm.space.repository.ApplicationRepository;
import com.itm.space.repository.ApplicationStatusRepository;
import com.itm.space.service.ChangeApplicationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.itm.space.constant.ErrorMessagesConstant.STATUS_NOT_FOUND_MESSAGE;

@Service
@AllArgsConstructor
public class ChangeApplicationServiceImpl implements ChangeApplicationService {

    private final ApplicationRepository applicationRepository;

    private final ApplicationStatusRepository applicationStatusRepository;

    @Override
    public ChangeApplicationResponse changeAndRetrieveApplication (ChangeApplicationRequest request) {
        ChangeApplicationResponse applicationResponse = new ChangeApplicationResponse();
        Application application = applicationInit(request);
        applicationResponse.setId(application.getId());
        applicationResponse.setComment(application.getComment());
        applicationResponse.setCreatedAt(application.getCreatedAt());
        applicationResponse.setUpdatedAt(application.getUpdatedAt());
        applicationResponse.setSkills(application.getSkills());
        applicationResponse.setExperience(application.getExperience());
        applicationResponse.setUserId(application.getUserId());
        applicationResponse.setSpecialization(application.getSpecialization());
        applicationResponse.setStatus(String.valueOf(application.getApplicationStatus().getName()));

        return applicationResponse;
    }

    private Application applicationInit (ChangeApplicationRequest request) {
        Application application = new Application();
        application.setId(UUID.randomUUID());
        application.setUserId(UUID.randomUUID());
        application.setSpecialization(request.getSpecialization());
        application.setSkills(request.getSkills());
        application.setExperience(request.getExperience());
        application.setApplicationStatus(applicationStatusRepository.findById(1).orElseThrow(() -> new EntityNotFoundException(STATUS_NOT_FOUND_MESSAGE)));
        application.setCreatedAt(LocalDateTime.now());
        application.setUpdatedAt(LocalDateTime.now());
        application.setComment(application.getComment());

        return applicationRepository.save(application);
    }
}
