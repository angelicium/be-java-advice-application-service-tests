package com.itm.space.service;

import com.itm.space.BaseUnitTest;
import com.itm.space.domain.entity.Application;
import com.itm.space.domain.entity.ApplicationStatus;
import com.itm.space.mapper.ApplicationMapper;
import com.itm.space.model.request.ApplicationCreateRequest;
import com.itm.space.model.response.ApplicationResponse;
import com.itm.space.repository.ApplicationRepository;
import com.itm.space.repository.ApplicationStatusRepository;
import com.itm.space.service.impl.ApplicationServiceImpl;
import com.itm.space.util.SecurityUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static com.itm.space.model.enums.ApplicationStatusName.CREATED;
import static com.itm.space.model.enums.ApplicationStatusName.PENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplicationServiceUnitTest extends BaseUnitTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private ApplicationStatusRepository statusRepository;

    @Mock
    private ApplicationMapper applicationMapper;

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    private final UUID testUserId = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
    private final UUID testAppId = UUID.fromString("f1e2d3c4-b5a6-7890-1234-567890abcdef");


    private ApplicationCreateRequest createRequest;
    private Application applicationEntity;
    private Application savedEntity;
    private ApplicationResponse response;
    private ApplicationStatus createdStatus;
    private MockedStatic<SecurityUtil> mockedSecurityUtil;

    @BeforeEach
    void setUp() {
        createRequest = new ApplicationCreateRequest(
                "Java Developer",
                "Spring Boot, Hibernate",
                "3 года"
        );

        createdStatus = new ApplicationStatus();
        createdStatus.setName(CREATED);

        applicationEntity = new Application();
        applicationEntity.setSpecialization(createRequest.getSpecialization());
        applicationEntity.setSkills(createRequest.getSkills());
        applicationEntity.setExperience(createRequest.getExperience());

        savedEntity = new Application();
        savedEntity.setId(testAppId);
        savedEntity.setUserId(testUserId);
        savedEntity.setSpecialization(createRequest.getSpecialization());
        savedEntity.setSkills(createRequest.getSkills());
        savedEntity.setExperience(createRequest.getExperience());
        savedEntity.setApplicationStatus(createdStatus);

        response = new ApplicationResponse();
        response.setId(testAppId);
        response.setUserId(testUserId);
        response.setSpecialization(createRequest.getSpecialization());
        response.setSkills(createRequest.getSkills());
        response.setExperience(createRequest.getExperience());
        response.setStatus(CREATED);

        mockedSecurityUtil = mockStatic(SecurityUtil.class);
        when(SecurityUtil.getCurrentUserId()).thenReturn(testUserId);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityUtil.close();
    }

    @Test
    @DisplayName("Успешное создание заявки")
    void createApplication_Success() {
        when(applicationRepository.countByUserIdAndApplicationStatus_NameIn(
                testUserId,
                List.of(CREATED, PENDING))
        ).thenReturn(0L);

        when(statusRepository.findByName(CREATED))
                .thenReturn(createdStatus);

        when(applicationMapper.toEntity(createRequest))
                .thenReturn(applicationEntity);

        when(applicationRepository.save(any(Application.class)))
                .thenReturn(savedEntity);

        when(applicationMapper.toResponse(savedEntity))
                .thenReturn(response);

        ApplicationResponse result = applicationService.createApplication(createRequest);


        assertNotNull(result);
        assertEquals(testAppId, result.getId());
        assertEquals(testUserId, result.getUserId());
        assertEquals("Java Developer", result.getSpecialization());
        assertEquals(CREATED, result.getStatus());

        verify(applicationRepository).countByUserIdAndApplicationStatus_NameIn(
                testUserId, List.of(CREATED, PENDING));
        verify(statusRepository).findByName(CREATED);
        verify(applicationMapper).toEntity(createRequest);
        verify(applicationRepository).save(applicationEntity);
        verify(applicationMapper).toResponse(savedEntity);
    }

    @Test
    @DisplayName("Ошибка при создании заявки, когда она уже существует")
    void createApplication_Conflict_ActiveApplicationExists() {
        when(applicationRepository.countByUserIdAndApplicationStatus_NameIn(
                testUserId,
                List.of(CREATED, PENDING))
        ).thenReturn(1L);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> applicationService.createApplication(createRequest)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        assertEquals("У пользователя уже есть активная заявка", exception.getReason());

        verify(statusRepository, never()).findByName(any());
        verify(applicationMapper, never()).toEntity(any());
        verify(applicationRepository, never()).save(any());
        verify(applicationMapper, never()).toResponse(any());
    }

    @Test
    void createApplication_VerifyEntitySetup() {
        when(applicationRepository.countByUserIdAndApplicationStatus_NameIn(
                testUserId,
                List.of(CREATED, PENDING))
        ).thenReturn(0L);

        when(statusRepository.findByName(CREATED))
                .thenReturn(createdStatus);

        when(applicationMapper.toEntity(createRequest))
                .thenReturn(applicationEntity);

        when(applicationRepository.save(any(Application.class)))
                .thenAnswer(invocation -> {
                    Application saved = invocation.getArgument(0);
                    assertNotNull(saved.getUserId());
                    assertNotNull(saved.getApplicationStatus());
                    return savedEntity;
                });

        when(applicationMapper.toResponse(savedEntity))
                .thenReturn(response);

        applicationService.createApplication(createRequest);

        verify(applicationRepository).save(applicationEntity);
        assertEquals(testUserId, applicationEntity.getUserId());
        assertEquals(createdStatus, applicationEntity.getApplicationStatus());
    }
}