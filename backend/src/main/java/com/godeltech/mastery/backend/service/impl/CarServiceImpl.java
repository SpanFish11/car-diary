package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.mapper.CarMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;
  private final CarMapper carMapper;

  @Override
  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  @Override
  public Car addCar(final CarCreateRequest carCreateRequest) {
    return carRepository.save(carMapper.map(carCreateRequest));
  }
}
