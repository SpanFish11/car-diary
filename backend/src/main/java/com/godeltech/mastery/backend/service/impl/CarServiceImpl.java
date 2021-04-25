package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.CarMapper;
import com.godeltech.mastery.backend.repository.BrandRepository;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.service.AwsService;
import com.godeltech.mastery.backend.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;
  private final BrandRepository brandRepository;
  private final AwsService awsService;
  private final CarMapper mapper;

  @Value(value = "${aws.s3.images.defaultCarImage}")
  private String defaultCarImage;

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
    final Brand brand = getBrandById(carCreateRequest.getBrandId());
    final Model model = getModelById(carCreateRequest.getModelId(), brand);
    final Car car = mapper.map(carCreateRequest);
    car.setBrand(brand);
    car.setModel(model);
    car.setPhotoUrl(defaultCarImage);
    return carRepository.save(car).getId();
  }

  @Override
  public void updateCarPhoto(final Long id, final MultipartFile multipartFile) {
    final Car car = getCar(id);
    final String photoUrl = awsService.uploadImage(multipartFile, id);
    car.setPhotoUrl(photoUrl);
    carRepository.save(car);
  }

  private Car getCar(final Long id) {
    return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("car", id));
  }

  private Brand getBrandById(final Long id) {
    return brandRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("brand", id));
  }

  private Model getModelById(final Long id, final Brand brand) {
    return brand.getModels().stream()
        .filter(m -> m.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("model", id));
  }
}
