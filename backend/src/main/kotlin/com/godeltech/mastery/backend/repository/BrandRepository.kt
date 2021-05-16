package com.godeltech.mastery.backend.repository

import com.godeltech.mastery.backend.domain.entity.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long>