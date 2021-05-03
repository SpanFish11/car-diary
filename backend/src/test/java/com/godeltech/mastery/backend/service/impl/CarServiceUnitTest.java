package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.CarMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.service.AwsService;
import com.godeltech.mastery.backend.service.ClientService;
import com.godeltech.mastery.backend.service.EquipmentService;
import com.godeltech.mastery.backend.service.ModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;

@ExtendWith(SpringExtension.class)
class CarServiceUnitTest {

  @Mock private CarMapper carMapper;
  @Mock private CarRepository carRepository;
  @Mock private AwsService awsService;
  @Mock private ModelService modelService;
  @Mock private ClientService clientService;
  @Mock private EquipmentService equipmentService;

  @InjectMocks CarServiceImpl carService;

  @Test
  void getCarById_given_id_should_car_found() {
    final Long id = 1L;
    final var car = Car.builder().id(id).vin("25654985656").build();
    final var expected = CarDTO.builder().id(id).vin("25654985656").build();

    given(carRepository.findById(id)).willReturn(Optional.of(car));
    given(carMapper.map(car)).willReturn(expected);

    final var actual = carService.getCarById(id);
    assertThat(actual, is(expected));

    then(carRepository).should(only()).findById(argThat(id::equals));
    then(carMapper).should(only()).map(refEq(car));
  }

  @Test
  void getCarBy_given_id_should_car_notFound() {
    final Long id = 55L;
    final var expected = new EntityNotFoundException("car", id);
    final var message = expected.getMessage();

    given(carRepository.findById(id)).willReturn(empty());

    final var actual = assertThrows(EntityNotFoundException.class, () -> carService.getCarById(id));
    assertThat(actual.getMessage(), is(message));

    then(carRepository).should(only()).findById(argThat(id::equals));
  }

  @Test
  void addNewCar() {
    final Long clientId = 1L;
    final Long modelId = 2L;
    final Long equipmentId = 3L;
    final Long expected = 5L;

    final var request =
        CarCreateRequest.builder()
            .modelId(modelId)
            .year(2020)
            .vin("12345678901234567")
            .mileage(20)
            .price(new BigDecimal("20"))
            .equipmentId(equipmentId)
            .build();
    final var car = new Car();
    final var model = new Model(modelId, "Emgrand", new Brand());
    final var client =
        Client.builder()
            .id(clientId)
            .firstName("Mary")
            .lastName("Simmons")
            .email("padiwoh422@gridmire.com")
            .build();
    final var equipment = new Equipment(equipmentId, "Luxury", "Petrol", "Manual", 2D, 400);
    final var carForSave = car.toBuilder().client(client).equipment(equipment).model(model).build();
    final var savedCar = carForSave.toBuilder().id(expected).build();

    given(carMapper.map(request)).willReturn(car);
    given(modelService.getModelById(modelId)).willReturn(model);
    given(clientService.getClientById(clientId)).willReturn(client);
    given(equipmentService.getEquipmentById(equipmentId)).willReturn(equipment);
    given(carRepository.save(carForSave)).willReturn(savedCar);

    final var actual = carService.addNewCar(clientId, request);
    assertThat(actual, is(savedCar.getId()));

    then(carMapper).should(only()).map(refEq(request));
    then(modelService).should(only()).getModelById(argThat(modelId::equals));
    then(clientService).should(only()).getClientById(argThat(clientId::equals));
    then(equipmentService).should(only()).getEquipmentById(argThat(equipmentId::equals));
    then(carRepository).should(only()).save(argThat(carForSave::equals));
  }

  @Test
  void updateCarPhoto() {
    final Long id = 1L;
    final var multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    final var url = "some url";
    final var carExpected = Car.builder().id(id).vin("25654985656").build();
    final var car = Car.builder().id(id).photoUrl(url).vin("5454544848").build();

    given(awsService.uploadImage(multipartFile, id)).willReturn(url);
    given(carRepository.findById(id)).willReturn(Optional.of(carExpected));
    given(carRepository.save(carExpected)).willReturn(car);

    carService.updateCarPhoto(id, multipartFile);

    then(awsService)
        .should(only())
        .uploadImage(argThat(multipartFile::equals), argThat(id::equals));
    then(carRepository).should(atLeastOnce()).findById(argThat(id::equals));
    then(carRepository).should(atLeastOnce()).save(argThat(carExpected::equals));
  }

  @Test
  void updateCarPhoto_given_id_should_car_notFound() {
    final Long id = 60L;
    final var multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    final var url = "some url";
    final var expected = new EntityNotFoundException("car", id);
    final String message = expected.getMessage();

    given(carRepository.findById(id)).willReturn(empty());
    given(awsService.uploadImage(multipartFile, id)).willReturn(url);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> carService.updateCarPhoto(id, multipartFile));
    assertThat(actual.getMessage(), is(message));

    then(carRepository).should(only()).findById(argThat(id::equals));
  }

  @Test
  void updateCarPhoto_given_imageButAwsThrowException() {
    final Long id = 1L;
    final var car = Car.builder().id(id).build();
    final var multipartFile = new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    final var expected = new RuntimeException("Something went wrong while uploading a picture");
    final var message = expected.getMessage();

    given(carRepository.findById(id)).willReturn(Optional.of(car));
    given(awsService.uploadImage(multipartFile, id)).willThrow(expected);

    final var actual =
        assertThrows(RuntimeException.class, () -> carService.updateCarPhoto(id, multipartFile));
    assertThat(actual.getMessage(), is(message));

    then(awsService)
        .should(only())
        .uploadImage(argThat(multipartFile::equals), argThat(id::equals));
  }

  @Test
  void getAllCarsByClientId_givenId_shouldReturnAllClientCars() {
    final Long id = 1L;
    final var client =
        Client.builder()
            .id(id)
            .firstName("John")
            .lastName("Snow")
            .email("snow324@mail.com")
            .build();
    final var cars =
        List.of(
            Car.builder().client(client).vin("25654985656").build(),
            Car.builder().client(client).vin("1545542").build());
    final var expected =
        List.of(
            CarDTO.builder().vin("25654985656").build(), CarDTO.builder().vin("1545542").build());

    given(clientService.getClientById(id)).willReturn(client);
    given(carRepository.getAllByClient(client)).willReturn(cars);
    given(carMapper.map(cars)).willReturn(expected);

    final var actual = carService.getAllCarsByClientId(id);
    assertThat(actual, is(expected));

    then(clientService).should(only()).getClientById(argThat(id::equals));
    then(carRepository).should(only()).getAllByClient(argThat(client::equals));
    then(carMapper).should(only()).map(refEq(cars));
  }

  @Test
  void getAllCarsByClientId_givenId_should_clientNotFound() {
    final Long id = 48L;
    final var expected = new EntityNotFoundException("client", id);
    final var message = expected.getMessage();

    given(clientService.getClientById(id)).willThrow(expected);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> carService.getAllCarsByClientId(id));
    assertThat(actual.getMessage(), is(message));

    then(clientService).should(only()).getClientById(argThat(id::equals));
  }

  private <T> void assertThatObject(final Class<T> clazz, final T actual, final T expected) {
    assertThat(actual, isA(clazz));
    assertThat(actual, is(expected));
  }
}
