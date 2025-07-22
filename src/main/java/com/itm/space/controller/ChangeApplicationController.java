package com.itm.space.controller;

import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.constant.ApiConstant.CHANGE_APPLICATION_URL;
import static com.itm.space.constant.RoleConstant.USER;

@RequestMapping(CHANGE_APPLICATION_URL)
@Tag(name = "Change Application Controller", description = "Изменение заявки на становление консультантом")
public interface ChangeApplicationController {

    @PutMapping()
    @Secured(USER)
    @Operation(security = @SecurityRequirement(name = "keycloak_oauth_scheme"))
    ChangeApplicationResponse changeApplication(@RequestBody ChangeApplicationRequest request);
}
