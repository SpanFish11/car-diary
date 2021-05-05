package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.OperationMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.repository.OperationRepository;
import com.godeltech.mastery.backend.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

  private final OperationRepository operationRepository;
  private final CarRepository carRepository;
  private final OperationMapper operationMapper;

  @Override
  public Long createOperation(final Long carId, final OperationCreateRequest operation) {
    ServiceOperationRecord serviceOperationRecord = operationMapper.toEntity(operation);
    final Car car =
        carRepository.findById(carId).orElseThrow(() -> new EntityNotFoundException("car", carId));
    serviceOperationRecord.setCar(car);
    return operationRepository.save(serviceOperationRecord).getId();
  }
}
