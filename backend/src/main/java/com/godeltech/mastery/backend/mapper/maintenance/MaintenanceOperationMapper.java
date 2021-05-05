package com.godeltech.mastery.backend.mapper.maintenance;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceOperationDTO;
import com.godeltech.mastery.backend.domain.entity.maintenance.MaintenanceOperation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MaintenanceOperationMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  MaintenanceOperationDTO toDTO(MaintenanceOperation operation);

  List<MaintenanceOperationDTO> toDTOList(List<MaintenanceOperation> operations);
}
