package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.entity.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {

  List<Car> getAllCars();

  Car addCar(CarCreateRequest carCreateRequest);
}
