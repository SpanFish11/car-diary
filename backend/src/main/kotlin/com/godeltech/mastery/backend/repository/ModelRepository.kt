package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Model
import org.springframework.data.jpa.repository.JpaRepository

interface ModelRepository : JpaRepository<Model, Long>