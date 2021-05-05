package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.ServiceWorkCreateRequest;
import com.godeltech.mastery.backend.domain.entity.ServiceWork;
import org.mapstruct.Mapping;

import java.util.List;

public interface ServiceWorkMapper {

    @Mapping(target = "", source = "")
    @Mapping(target = "", source = "")
    @Mapping(target = "", source = "")
    ServiceWork toEntity(ServiceWorkCreateRequest request);

    List<ServiceWork> toListService(List<ServiceWorkCreateRequest> requests);
}
