package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;

import java.util.List;

public interface MaintenanceService {

  List<MaintenanceDTO> getAllMaintenance();
}
