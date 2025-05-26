package com.itm.space.service;

import com.itm.space.model.request.ExampleRequest;
import com.itm.space.model.response.ExampleResponse;

public interface ExampleService {

    ExampleResponse handleExampleRequest(ExampleRequest request);
}
