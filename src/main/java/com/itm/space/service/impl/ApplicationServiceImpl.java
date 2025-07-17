package com.itm.space.service.impl;

import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.mapper.ApplicationMapper;
import com.itm.space.model.request.ApplicationCreateRequest;
import com.itm.space.model.response.ApplicationResponse;
import com.itm.space.repository.ApplicationRepository;
import com.itm.space.repository.ApplicationStatusRepository;
import com.itm.space.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static com.itm.space.model.enums.ApplicationStatusName.CREATED;
import static com.itm.space.model.enums.ApplicationStatusName.PENDING;
import static com.itm.space.util.SecurityUtil.getCurrentUserId;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationStatusRepository statusRepository;
    private final ApplicationMapper applicationMapper;

    @Override
    @Transactional
    public ApplicationResponse createApplication(ApplicationCreateRequest request) {
        UUID userId = getCurrentUserId();

        long count = applicationRepository.countByUserIdAndApplicationStatus_NameIn(userId, List.of(CREATED, PENDING));
        if (count > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "У пользователя уже есть активная заявка");
        }

        ApplicationStatus status = statusRepository.findByName(CREATED);

        Application entity = applicationMapper.toEntity(request);
        entity.setUserId(userId);
        entity.setApplicationStatus(status);

        Application saved = applicationRepository.save(entity);

        return applicationMapper.toResponse(saved);
    }
}
