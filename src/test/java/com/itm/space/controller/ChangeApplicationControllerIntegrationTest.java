package com.itm.space.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.constant.RoleConstant;
import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static com.itm.space.constant.ErrorMessagesConstant.*;
import static com.itm.space.constant.RoleConstant.USER;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangeApplicationControllerIntegrationTest extends BaseIntegrationTest {

    private ChangeApplicationRequest validRequest;

    @BeforeEach
    void setUp() {

        validRequest = ChangeApplicationRequest.builder()
                .skills("test skills")
                .specialization("test specialization")
                .experience("test experience")
                .build();
    }

    @Test
    @DisplayName("Изменение заявки. Статус 200: успешно")
    @DataSet(value = "/datasets/controller/сhangeApplicationController/01-currentUser.yaml")
    @Transactional
    void changeApplication() throws Exception {
        String accessToken = authUtil.getAuthorization("test_user");
        mockMvc.perform(put("/api/v1/applications/a1b2c3d4-e5f6-7890-1234-567890abcdef")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skills").value(validRequest.getSkills()))
                .andExpect(jsonPath("$.specialization").value(validRequest.getSpecialization()))
                .andExpect(jsonPath("$.experience").value(validRequest.getExperience()));
//                .andExpect(jsonPath("$.updatedAt").exists()); на бд testcontainers не создает поля как и в applicationControllerIT
    }

    @Test
    @DisplayName("Изменение заявки. Статус 400: Неправильные параметры запроса")
    @DataSet(value = "/datasets/controller/сhangeApplicationController/01-currentUser.yaml")
    void shouldReturn400WhenBadRequest() throws Exception {
        String accessToken = authUtil.getAuthorization("test_user");
        mockMvc.perform(put("/api/v1/applications/123e4567-e89b-12d3-a456-426614174000")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Изменение заявки. Статус 401: Пользователь не аутентифицирован")
    void shouldReturn401WhenNotAuthorized() throws Exception {
        String applicationId = "123e4567-e89b-12d3-a456-426614174000"; // Пример ID
        mockMvc.perform(put("/api/v1/applications/" + applicationId) // Подставляем ID в URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(UNAUTHORIZED_MESSAGE));
    }


    @Test
    @WithMockUser(authorities = "OTHER_ROLE")
    @DisplayName("Изменение заявки. Статус 403: Недостаточно прав пользователя")
    void shouldReturn403WhenForbidden() throws Exception {
        String applicationId = "123e4567-e89b-12d3-a456-426614174000"; // Пример ID
        mockMvc.perform(put("/api/v1/applications/" + applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Access Denied")); // Изменить здесь
    }

    @Test
    @WithMockUser
    @DisplayName("Изменение заявки. Статус 404: Заявка не найдена")
    void shouldReturn404WhenNotFound() throws Exception {

        mockMvc.perform(put("/api/v1/applications/6aa45e75-7843-8921-b3fc-3a074a77bbb6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(APPLICATION_NOT_FOUND));
    }
}