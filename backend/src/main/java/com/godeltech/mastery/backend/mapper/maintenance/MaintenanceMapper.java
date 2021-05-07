package com.godeltech.mastery.backend.mapper.maintenance;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;
import com.godeltech.mastery.backend.domain.entity.maintenance.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {MaintenanceOperationMapper.class, DetailMapper.class})
public interface MaintenanceMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "operationNumber", source = "operationNumber")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "timeInterval", source = "timeInterval")
  @Mapping(target = "operations", source = "operations")
  @Mapping(target = "details", source = "details")
  MaintenanceDTO toDTO(Maintenance maintenance);

  List<MaintenanceDTO> toDTOList(List<Maintenance> maintenances);
}
