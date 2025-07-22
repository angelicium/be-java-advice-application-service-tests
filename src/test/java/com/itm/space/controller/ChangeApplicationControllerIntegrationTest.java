package com.itm.space.controller;

import com.itm.space.BaseIntegrationTest;
import com.itm.space.model.request.ChangeApplicationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static com.itm.space.constant.ErrorMessagesConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangeApplicationControllerIntegrationTest extends BaseIntegrationTest {

    private ChangeApplicationRequest validRequest;
    private ChangeApplicationRequest invalidRequest;

    @BeforeEach
    void setUp() {

        validRequest = ChangeApplicationRequest.builder()
                .skills("test skills")
                .specialization("test specialization")
                .experience("test experience")
                .build();

        invalidRequest = ChangeApplicationRequest.builder()
                .skills("")
                .specialization("specialization")
                .experience("experience")
                .build();
    }

    @Test
    @WithMockUser
    @DisplayName("Изменение заявки. Статус 200: успешно")
    void changeApplication() throws Exception {

        mockMvc.perform(put("/api/v1/applications/{applicationId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skills").value(validRequest.getSkills()))
                .andExpect(jsonPath("$.specialization").value(validRequest.getSpecialization()))
                .andExpect(jsonPath("$.experience").value(validRequest.getExperience()));
    }

    @Test
    @WithMockUser
    @DisplayName("Изменение заявки. Статус 400: Неправильные параметры запроса")
    void shouldReturn400WhenBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/applications/{applicationId}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(BAD_REQUEST_MESSAGE));
    }

    @Test
    @DisplayName("Изменение заявки. Статус 401: Пользователь не аутентифицирован")
    void shouldReturn401WhenNotAuthorized() throws Exception {

        mockMvc.perform(post("/api/v1/applications/{applicationId}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value(UNAUTHORIZED_MESSAGE));
    }

    @Test
    @WithMockUser(authorities = "OTHER_ROLE")
    @DisplayName("Изменение заявки. Статус 403: Недостаточно прав пользователя")
    void shouldReturn403WhenForbidden() throws Exception {

        mockMvc.perform(post("/api/v1/applications/{applicationId}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value(FORBIDDEN_MESSAGE));
    }

    @Test
    @WithMockUser
    @DisplayName("Изменение заявки. Статус 404: Заявка не найдена")
    void shouldReturn404WhenNotFound() throws Exception {

        mockMvc.perform(post("/api/v1/applications/{applicationId}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(APPLICATION_NOT_FOUND));
    }
}
