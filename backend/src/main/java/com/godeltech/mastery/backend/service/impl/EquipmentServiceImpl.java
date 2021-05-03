package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.EquipmentMapper;
import com.godeltech.mastery.backend.repository.EquipmentRepository;
import com.godeltech.mastery.backend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

  private final EquipmentRepository equipmentRepository;
  private final EquipmentMapper equipmentMapper;

  @Override
  public Equipment getEquipmentById(final Long id) {
    return equipmentRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("equipment", id));
  }

  @Override
  public List<EquipmentDTO> getEquipments() {
    return equipmentMapper.toDtos(equipmentRepository.findAll());
  }
}
