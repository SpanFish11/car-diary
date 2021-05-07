package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.CarMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.service.AwsService;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.ClientService;
import com.godeltech.mastery.backend.service.EquipmentService;
import com.godeltech.mastery.backend.service.ModelService;
import com.godeltech.mastery.backend.specification.impl.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.data.domain.PageRequest.of;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;
  private final ClientService clientService;
  private final EquipmentService equipmentService;
  private final AwsService awsService;
  private final ModelService modelService;
  private final CarMapper carMapper;
  private final CarSpecification carSpecification;

  @Value(value = "${aws.s3.images.defaultCarImage}")
  private String defaultCarImage;

  @Override
  public Page<CarDTO> getAllCarsOrFindByFilter(
      final Filter filter, final Integer page, final Integer pageSize) {
    return carRepository
        .findAll(carSpecification.getFilter(filter), of(page, pageSize))
        .map(carMapper::map);
  }

  @Override
  public Car findCarById(Long id) {
    return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("car", id));
  }

  @Override
  public CarDTO getCarById(final Long id) {
    return carMapper.map(findCarById(id));
  }

  @Override
  public Long addNewCar(final Long clientId, final CarCreateRequest request) {
    final var car = carMapper.map(request);
    final var savedCar = saveCar(clientId, request, car);
    return savedCar.getId();
  }

  @Override
  public Long addNewCar(final CarCreateManagerRequest request) {
    final var car = carMapper.map(request);
    final var savedCar = saveCar(request.getClientId(), request, car);
    return savedCar.getId();
  }

  @Override
  public void updateCarPhoto(final Long id, final MultipartFile multipartFile) {
    final var car = findCarById(id);
    final var photoUrl = awsService.uploadImage(multipartFile, id);
    car.setPhotoUrl(photoUrl);
    carRepository.save(car);
  }

  @Override
  public List<CarDTO> getAllCarsByClientId(final Long clientId) {
    final var client = clientService.getClientById(clientId);
    return carMapper.map(carRepository.getAllByClient(client));
  }

  @Override
  public List<CarDTO> getCurrentClientCars(final Authentication principal) {
    final var client = clientService.getClient(principal);
    return carMapper.map(carRepository.getAllByClient(client));
  }

  private <T extends CarCreateRequest> Car saveCar(
      final Long clientId, final T request, final Car car) {
    final var client = clientService.getClientById(clientId);
    final var model = modelService.getModelById(request.getModelId());
    final var equipment = equipmentService.getEquipmentById(request.getEquipmentId());
    final var carForSave =
        car.toBuilder()
            .model(model)
            .client(client)
            .equipment(equipment)
            .photoUrl(defaultCarImage)
            .build();
    if (null == carForSave.getOurs()) {
      carForSave.setOurs(FALSE);
    }
    if (null == carForSave.getUsed()) {
      carForSave.setUsed(TRUE);
    }
    return carRepository.save(carForSave);
  }
}
