package com.itm.space.controller.impl;

import com.itm.space.controller.ChangeApplicationController;
import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;
import com.itm.space.service.ChangeApplicationService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChangeApplicationControllerImpl implements ChangeApplicationController {

    private final ChangeApplicationService changeApplicationService;

    @Override
    public ChangeApplicationResponse changeApplication(ChangeApplicationRequest request) {

        return changeApplicationService.changeAndRetrieveApplication(request);
    }
}
