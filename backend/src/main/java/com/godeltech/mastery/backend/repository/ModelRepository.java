package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {}
