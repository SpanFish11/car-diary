package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.dto.Filter;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.CarMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.repository.ModelRepository;
import com.godeltech.mastery.backend.service.AwsService;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.specification.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;
  private final AwsService awsService;
  private final ModelRepository modelRepository;
  private final CarMapper mapper;

  @Value(value = "${aws.s3.images.defaultCarImage}")
  private String defaultCarImage;

  @Override
  public Page<CarDTO> getAllCarsOrFindByFilter(
      final Filter filter, final Integer page, final Integer pageSize) {
    Specification<Car> specification =
        where(CarSpecification.withVin(filter.getVin()))
            .and(CarSpecification.withOwnerLastname(filter.getLastname()))
            .and(CarSpecification.withYear(filter.getSpecificYear()))
            .and(CarSpecification.greaterOrEqualYear(filter.getFrom()))
            .and(CarSpecification.withModel(filter.getModelId()))
            .and(CarSpecification.lessOrEqualYear(filter.getUntil()));
    return carRepository.findAll(specification, of(page, pageSize)).map(mapper::map);
  }

  @Override
  public List<CarDTO> getAllCars() {
    return mapper.map(carRepository.findAll());
  }

  @Override
  public CarDTO getCarById(final Long id) {
    return mapper.map(getCar(id));
  }

  @Override
  public Long addNewCar(final CarCreateRequest carCreateRequest) {
    final var model = getModelById(carCreateRequest.getModelId());
    final var car = mapper.map(carCreateRequest);
    car.setModel(model);
    car.setPhotoUrl(defaultCarImage);
    return carRepository.save(car).getId();
  }

  @Override
  public void updateCarPhoto(final Long id, final MultipartFile multipartFile) {
    final var car = getCar(id);
    final var photoUrl = awsService.uploadImage(multipartFile, id);
    car.setPhotoUrl(photoUrl);
    carRepository.save(car);
  }

  private Car getCar(final Long id) {
    return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("car", id));
  }

  private Model getModelById(final Long id) {
    return modelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("model", id));
  }
}
