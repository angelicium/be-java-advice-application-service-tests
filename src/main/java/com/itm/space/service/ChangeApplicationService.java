package com.itm.space.service;

import com.itm.space.model.request.ChangeApplicationRequest;
import com.itm.space.model.response.ChangeApplicationResponse;

import java.util.UUID;

public interface ChangeApplicationService {

    ChangeApplicationResponse changeAndRetrieveApplication (ChangeApplicationRequest request, UUID id);
}
