package com.itm.space.controller;

import com.itm.space.constant.RoleConstant;
import com.itm.space.model.request.ApplicationCreateRequest;
import com.itm.space.model.response.ApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.constant.ApiConstant.APPLICATION_URL;

@RequestMapping(APPLICATION_URL)
@Tag(name = "Application controller", description = "Контроллер для работы с заявками на консультанта")
public interface ApplicationController {

    @PostMapping
    @Secured(RoleConstant.USER)
    @Operation(
            summary = "Создание заявки на консультанта",
            description = "Метод позволяет пользователю создать заявку на становление консультантом",
            security = @SecurityRequirement(name = "keycloak_oauth_scheme")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заявка успешно создана"),
            @ApiResponse(responseCode = "400", description = "Неправильные параметры запроса"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Недостаточно прав пользователя"),
            @ApiResponse(responseCode = "409", description = "Активная заявка уже существует"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    ResponseEntity<ApplicationResponse> createApplication(@RequestBody ApplicationCreateRequest request);
}
