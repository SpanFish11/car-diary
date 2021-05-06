package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {

  Page<CarDTO> getAllCarsOrFindByFilter(Filter filter, Integer page, Integer pageSize);

  CarDTO getCarById(Long id);

  Long addNewCar(Long clientId, CarCreateRequest carCreateRequest);

  Long addNewCar(CarCreateManagerRequest request);

  void updateCarPhoto(Long id, MultipartFile multipartFile);

  List<CarDTO> getAllCarsByClientId(Long id);

  List<CarDTO> getCurrentClientCars(Authentication principal);
}
