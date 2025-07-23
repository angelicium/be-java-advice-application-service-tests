package com.itm.space.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.database.rider.core.api.dataset.DataSet;
import com.itm.space.BaseIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.itm.space.constant.ApiConstant.APPLICATION_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApplicationControllerIT extends BaseIntegrationTest{

    private final String testRequest = jsonParserUtil.getStringFromJson("json/controller/request/create-application-request.json");

    public ApplicationControllerIT() throws JsonParseException {
    }

    @Test
    @DisplayName("Создание заявки: 200 — успешно")
    @DataSet(value = "/datasets/controller/applicationController/post/create.yml", cleanBefore = true, cleanAfter = true)
    void shouldCreateApplicationSuccessfully() throws Exception {
        String accessToken = authUtil.getAuthorization("test_user");

        mockMvc.perform(MockMvcRequestBuilders.post(APPLICATION_URL)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(testRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.specialization").value("Java Backend"))
                .andExpect(jsonPath("$.skills").value("Java, Spring Boot"))
                .andExpect(jsonPath("$.experience").value("3 года в Яндексе"))
                .andExpect(jsonPath("$.status").value("CREATED"));
//                .andExpect(jsonPath("$.createdAt").exists()); на бд testcontainers не создает поля как и в ChangeApplicationControllerIntegrationTest
    }

    @Test
    @DisplayName("Создание заявки: 400 - Ошибка валидации полей")
    @DataSet(value = "/datasets/controller/applicationController/post/create.yml", cleanBefore = true, cleanAfter = true)
    void shouldReturn400IfInvalidRequest() throws Exception {
        String accessToken = authUtil.getAuthorization("test_user");
        String invalidRequestJson = "{\"skills\":\"Java, Spring Boot\", \"experience\":\"3 года в Яндексе\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(APPLICATION_URL)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Создание заявки: 401 - Неавторизованный пользователь")
    void shouldReturn401IfUnauthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(APPLICATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(testRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.type").value("Unauthorized"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Создание заявки: 403 - Нет нужной роли")
    @WithMockUser(username = "41d15d3e-f109-497a-8341-5e4e6f40a1ef", authorities = "ROLE_ADMIN")
    void shouldReturn403IfForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(APPLICATION_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(testRequest))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Создание заявки: 409 - У пользователя уже есть активная заявка")
    @DataSet(value = "/datasets/controller/applicationController/post/already_exists.yml",
            cleanBefore = true, cleanAfter = true)
    void shouldReturn409IfApplicationAlreadyExists() throws Exception {
        String accessToken = authUtil.getAuthorization("test_user");

        mockMvc.perform(MockMvcRequestBuilders.post(APPLICATION_URL)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testRequest))
                .andExpect(status().isConflict());
    }
}