package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<ServiceOperationRecord, Long> {}
