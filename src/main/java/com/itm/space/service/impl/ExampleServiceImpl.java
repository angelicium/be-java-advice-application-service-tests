package com.itm.space.service.impl;

import com.itm.space.model.request.ExampleRequest;
import com.itm.space.model.response.ExampleResponse;
import com.itm.space.service.ExampleService;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl implements ExampleService {

    @Override
    public ExampleResponse handleExampleRequest(ExampleRequest request) {
        return new ExampleResponse(String.format("Example response: %s", request.getMessage()));
    }
}
