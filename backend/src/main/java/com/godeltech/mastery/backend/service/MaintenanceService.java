package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDTO;

import java.util.List;

public interface MaintenanceService {

  List<MaintenanceDTO> getAllMaintenance();
}
