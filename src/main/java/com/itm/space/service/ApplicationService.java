package com.itm.space.service;

import com.itm.space.model.request.ApplicationCreateRequest;
import com.itm.space.model.response.ApplicationResponse;

public interface ApplicationService {

    ApplicationResponse createApplication(ApplicationCreateRequest request);
}
