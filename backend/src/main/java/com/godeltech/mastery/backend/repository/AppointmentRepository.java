package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {}
