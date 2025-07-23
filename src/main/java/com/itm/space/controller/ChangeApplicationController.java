package com.itm.space.controller;

import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static com.itm.space.constant.ApiConstant.APPLICATION_URL;
import static com.itm.space.constant.RoleConstant.USER;

@RequestMapping(APPLICATION_URL)
@Tag(name = "Change Application Controller", description = "Изменение заявки на становление консультантом")
public interface ChangeApplicationController {

    @PutMapping("/{id}")
    @Secured(USER)
    @Operation(security = @SecurityRequirement(name = "keycloak_oauth_scheme"))
    ChangeApplicationResponse changeApplication(@RequestBody @Valid ChangeApplicationRequest request, @PathVariable("id") UUID id);
}