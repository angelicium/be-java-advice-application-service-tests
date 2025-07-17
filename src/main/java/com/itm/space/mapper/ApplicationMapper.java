package com.itm.space.mapper;

import com.itm.space.domain.entity.Application;
import com.itm.space.model.request.ApplicationCreateRequest;
import com.itm.space.model.response.ApplicationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "applicationStatus", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Application toEntity(ApplicationCreateRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "status", source = "applicationStatus.name")
    ApplicationResponse toResponse(Application entity);
}

