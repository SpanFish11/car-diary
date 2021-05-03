package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {}
