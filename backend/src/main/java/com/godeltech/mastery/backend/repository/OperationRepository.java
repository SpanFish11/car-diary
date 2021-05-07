package com.godeltech.mastery.backend.repository;

import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<ServiceOperationRecord, Long> {

  Optional<List<ServiceOperationRecord>> getAllByCar(Car car);
}
