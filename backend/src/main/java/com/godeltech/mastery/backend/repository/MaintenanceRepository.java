package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {}
