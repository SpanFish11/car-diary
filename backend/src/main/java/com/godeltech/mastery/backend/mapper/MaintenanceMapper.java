package com.godeltech.mastery.backend.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDTO;
import com.godeltech.mastery.backend.domain.entity.Maintenance;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {MaintenanceOperationMapper.class, DetailMapper.class},
    unmappedTargetPolicy = IGNORE)
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
