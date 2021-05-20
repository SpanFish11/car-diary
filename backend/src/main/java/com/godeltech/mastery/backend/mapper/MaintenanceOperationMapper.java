package com.godeltech.mastery.backend.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceOperationDTO;
import com.godeltech.mastery.backend.domain.entity.MaintenanceOperation;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface MaintenanceOperationMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  MaintenanceOperationDTO toDTO(MaintenanceOperation operation);

  List<MaintenanceOperationDTO> toDTOList(List<MaintenanceOperation> operations);
}
