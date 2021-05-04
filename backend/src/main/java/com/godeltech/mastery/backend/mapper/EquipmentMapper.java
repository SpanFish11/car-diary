package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

  EquipmentDTO toDto(Equipment equipment);

  List<EquipmentDTO> toDtos(List<Equipment> equipment);
}
