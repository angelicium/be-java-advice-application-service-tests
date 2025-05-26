package com.itm.space.controller;

import com.itm.space.constant.RoleConstant;
import com.itm.space.model.request.ExampleRequest;
import com.itm.space.model.response.ExampleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.itm.space.constant.ApiConstant.EXAMPLE_URL;

@RequestMapping(EXAMPLE_URL)
@Tag(name = "Test Controller", description = "Тестовый контроллер")
public interface ExampleController {

    @PostMapping
    @Secured(RoleConstant.USER)
    @Operation(security = @SecurityRequirement(name = "keycloak_oauth_scheme"))
    ExampleResponse exampleRequest(@RequestBody ExampleRequest request);
}
