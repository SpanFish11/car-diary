package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.entity.Appointment;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OperationMapper.class, CarMapper.class})
public interface AppointmentMapper {

  @Mapping(target = "repairment", source = "repairment")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "date", source = "date")
  Appointment toEntity(AppointmentCreateRequest createRequest);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "date", source = "date")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "car", source = "car")
  @Mapping(target = "repairment", source = "repairment")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "regularService", source = "regularService")
  AppointmentDTO toDTO(Appointment appointment);

  List<AppointmentDTO> toListDTO(List<Appointment> appointments);
}
