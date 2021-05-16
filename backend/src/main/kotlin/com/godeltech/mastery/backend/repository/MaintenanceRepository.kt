package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Maintenance
import org.springframework.data.jpa.repository.JpaRepository

interface MaintenanceRepository : JpaRepository<Maintenance, Long>