package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {}
