package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ServiceWorkMapper.class, ChangablePartMapper.class})
public interface OperationMapper {

  @Mapping(target = "serviceOperationNumber", source = "serviceOperationNumber")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "serviceWorks", source = "serviceWorks")
  @Mapping(target = "changableParts", source = "changableParts")
  ServiceOperationRecord toEntity(OperationCreateRequest operationCreateRequest);
}
