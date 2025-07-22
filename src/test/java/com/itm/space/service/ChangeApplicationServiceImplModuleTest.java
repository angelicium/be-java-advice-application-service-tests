package com.itm.space.service;

import com.itm.space.BaseUnitTest;
import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import com.itm.space.repository.ApplicationRepository;
import com.itm.space.repository.ApplicationStatusRepository;
import com.itm.space.service.impl.ChangeApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class ChangeApplicationServiceImplModuleTest extends BaseUnitTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationStatusRepository applicationStatusRepository;

    private ChangeApplicationRequest request;
    private Application application;
    private ChangeApplicationResponse response;

    @InjectMocks
    private ChangeApplicationServiceImpl changeApplicationServiceImpl;

    @BeforeEach
    void setUp() {
        request = ChangeApplicationRequest.builder()
                .skills("skills")
                .specialization("specialization")
                .experience("experience")
                .build();

        application = Application.builder()
                .id(UUID.randomUUID())
                .applicationStatus(ApplicationStatus.builder().build())
                .comment("comment")
                .createdAt(LocalDateTime.now())
                .userId(UUID.randomUUID())
                .skills("skills")
                .specialization("specialization")
                .updatedAt(LocalDateTime.now())
                .experience("experience")
                .build();

        response = ChangeApplicationResponse.builder()
                .comment("comment")
                .createdAt(LocalDateTime.now())
                .status("status")
                .id(UUID.randomUUID())
                .skills("skills")
                .specialization("specialization")
                .userId(UUID.randomUUID())
                .build();
    }

    @Test
    public void changeApplicationTest() {

        when(applicationRepository.findById(application.getId())).thenReturn(Optional.of(application));
        when(applicationStatusRepository.findById()


    }



}
