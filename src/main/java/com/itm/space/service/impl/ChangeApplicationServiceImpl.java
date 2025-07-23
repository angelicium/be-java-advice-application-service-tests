package com.itm.space.service.impl;

import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import com.itm.space.repository.ApplicationRepository;
import com.itm.space.service.ChangeApplicationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.itm.space.constant.ErrorMessagesConstant.APPLICATION_NOT_FOUND;
import static com.itm.space.constant.ErrorMessagesConstant.BAD_REQUEST_MESSAGE;
import static com.itm.space.constant.ErrorMessagesConstant.FORBIDDEN_MESSAGE;
import static com.itm.space.util.SecurityUtil.getCurrentUserId;
import static com.itm.space.model.enums.ApplicationStatusName.CREATED;

@Service
@AllArgsConstructor
public class ChangeApplicationServiceImpl implements ChangeApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    @Transactional
    public ChangeApplicationResponse changeAndRetrieveApplication(ChangeApplicationRequest request, UUID id) {

        Application application = applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(APPLICATION_NOT_FOUND));

        filter(application.getUserId(), application.getApplicationStatus());

        application.setSpecialization(request.getSpecialization());
        application.setExperience(request.getExperience());
        application.setSkills(request.getSkills());
        Application saved = applicationRepository.save(application);

        return responseInit(saved);
    }

    private ChangeApplicationResponse responseInit(Application application) {
        ChangeApplicationResponse applicationResponse = new ChangeApplicationResponse();
        applicationResponse.setId(application.getId());
        applicationResponse.setComment(application.getComment());
        applicationResponse.setCreatedAt(application.getCreatedAt());
        applicationResponse.setUpdatedAt(application.getUpdatedAt());
        applicationResponse.setSkills(application.getSkills());
        applicationResponse.setExperience(application.getExperience());
        applicationResponse.setUserId(application.getUserId());
        applicationResponse.setSpecialization(application.getSpecialization());
        applicationResponse.setStatus(application.getApplicationStatus().getName().name());

        return applicationResponse;
    }

    private void filter(UUID userId, ApplicationStatus status) {
        UUID currentUserId = getCurrentUserId();
        if (!userId.equals(currentUserId)) {
            throw new AuthorizationDeniedException(FORBIDDEN_MESSAGE);
        }
        if (status.getName() != CREATED) {
            throw new IllegalArgumentException(BAD_REQUEST_MESSAGE);
        }
    }
}
