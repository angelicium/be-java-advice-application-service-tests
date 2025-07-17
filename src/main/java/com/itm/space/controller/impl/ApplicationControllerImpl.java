package com.itm.space.controller.impl;

import com.itm.space.controller.ApplicationController;
import com.itm.space.model.request.ApplicationCreateRequest;
import com.itm.space.model.response.ApplicationResponse;
import com.itm.space.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class ApplicationControllerImpl implements ApplicationController {

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<ApplicationResponse> createApplication(ApplicationCreateRequest request) {

        ApplicationResponse response = applicationService.createApplication(request);
        return ResponseEntity.status(CREATED).body(response);
    }
}
