package com.itm.space.controller.impl;

import com.itm.space.controller.ExampleController;
import com.itm.space.model.request.ExampleRequest;
import com.itm.space.model.response.ExampleResponse;
import com.itm.space.service.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleControllerImpl implements ExampleController {
    private final ExampleService exampleService;

    @Override
    public ExampleResponse exampleRequest(ExampleRequest request) {
        return exampleService.handleExampleRequest(request);
    }
}
