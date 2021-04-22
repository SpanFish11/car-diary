package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CarService {

  List<CarDTO> getAllCars();

  CarDTO getCarById(Long id);

  Long addNewCar(CarCreateRequest carCreateRequest);

  void updateCarPhoto(Long id, MultipartFile multipartFile);
}
