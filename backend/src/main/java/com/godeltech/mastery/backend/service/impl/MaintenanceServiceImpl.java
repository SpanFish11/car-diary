package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;
import com.godeltech.mastery.backend.mapper.maintenance.MaintenanceMapper;
import com.godeltech.mastery.backend.repository.MaintenanceRepository;
import com.godeltech.mastery.backend.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

  private final MaintenanceRepository maintenanceRepository;
  private final MaintenanceMapper maintenanceMapper;

  @Override
  public List<MaintenanceDTO> getAllMaintenance() {
    return maintenanceMapper.toDTOList(maintenanceRepository.findAll());
  }
}
