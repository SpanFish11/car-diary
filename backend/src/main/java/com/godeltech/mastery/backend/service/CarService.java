package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.dto.Filter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CarService {

  Page<CarDTO> getAllCarsOrFindByFilter(
      final Filter filter, final Integer page, final Integer pageSize);

  List<CarDTO> getAllCars();

  CarDTO getCarById(final Long id);

  Long addNewCar(final CarCreateRequest carCreateRequest);

  void updateCarPhoto(final Long id, final MultipartFile multipartFile);
}
