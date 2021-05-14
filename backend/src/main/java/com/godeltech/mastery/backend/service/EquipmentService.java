package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.entity.Equipment;

import java.util.List;

public interface EquipmentService {

  Equipment getEquipmentById(Long id);

  List<EquipmentDTO> getEquipments();
}
