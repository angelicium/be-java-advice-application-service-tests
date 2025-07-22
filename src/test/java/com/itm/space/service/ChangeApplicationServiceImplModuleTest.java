package com.itm.space.service;

import com.itm.space.BaseUnitTest;
import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import com.itm.space.repository.ApplicationRepository;
import com.itm.space.service.impl.ChangeApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.itm.space.util.SecurityUtil;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.itm.space.model.enums.ApplicationStatusName.CREATED;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ChangeApplicationServiceImplModuleTest extends BaseUnitTest {

    @Mock
    private ApplicationRepository applicationRepository;

    private ChangeApplicationRequest request;
    private Application application;
    private MockedStatic<SecurityUtil> mockedSecurityUtil;

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
                .applicationStatus(ApplicationStatus.builder()
                        .name(CREATED)
                        .description("description")
                        .id(1)
                        .build())
                .comment("comment")
                .createdAt(LocalDateTime.now())
                .userId(UUID.randomUUID())
                .skills("skills")
                .specialization("specialization")
                .updatedAt(LocalDateTime.now())
                .experience("experience")
                .build();

        mockedSecurityUtil = mockStatic(SecurityUtil.class);
    }

    @Test
    public void changeApplicationTest() {

        when(applicationRepository.findById(application.getId())).thenReturn(Optional.of(application));
        when(SecurityUtil.getCurrentUserId()).thenReturn(application.getUserId());
        when(applicationRepository.save(application)).thenReturn(application);

        ChangeApplicationResponse applicationResponse = changeApplicationServiceImpl.changeAndRetrieveApplication(request, application.getId());

        assertNotNull(applicationResponse);
        assertEquals(application.getId(), applicationResponse.getId());
        assertEquals(application.getComment(), applicationResponse.getComment());
        assertEquals(application.getCreatedAt(), applicationResponse.getCreatedAt());
        assertEquals(application.getUserId(), applicationResponse.getUserId());
        assertEquals(application.getSkills(), applicationResponse.getSkills());
        assertEquals(application.getSpecialization(), applicationResponse.getSpecialization());
        assertEquals(application.getUpdatedAt(), applicationResponse.getUpdatedAt());

        verify(applicationRepository).save(any(Application.class));
    }
}