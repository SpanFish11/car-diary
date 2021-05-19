package com.godeltech.mastery.backend.mapper.maintenance;

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceOperationDTO;
import com.godeltech.mastery.backend.domain.entity.MaintenanceOperation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface MaintenanceOperationMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  MaintenanceOperationDTO toDTO(MaintenanceOperation operation);

  List<MaintenanceOperationDTO> toDTOList(List<MaintenanceOperation> operations);
}
