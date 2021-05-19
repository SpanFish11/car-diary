package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
    componentModel = "spring",
    uses = {ServiceWorkMapper.class, ChangeablePartMapper.class},
    unmappedTargetPolicy = IGNORE)
public interface OperationMapper {

  @Mapping(target = "serviceOperationNumber", source = "serviceOperationNumber")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "serviceWorks", source = "serviceWorks")
  @Mapping(target = "changableParts", source = "changableParts")
  ServiceOperationRecord toEntity(OperationCreateRequest operationCreateRequest);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "serviceOperationNumber", source = "serviceOperationNumber")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "serviceWorks", source = "serviceWorks")
  @Mapping(target = "changableParts", source = "changableParts")
  ServiceOperationRecordDTO toDTO(ServiceOperationRecord serviceOperationRecord);

  List<ServiceOperationRecordDTO> toDTOList(List<ServiceOperationRecord> serviceOperationRecords);
}
