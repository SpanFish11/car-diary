package com.godeltech.mastery.backend.service.impl;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.springframework.data.domain.PageRequest.of;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ChangeMileageRequest;
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
import com.godeltech.mastery.backend.specification.impl.CarSpecification;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CarServiceUnitTest {

  @Mock CarRepository carRepository;
  @Mock ClientService clientService;
  @Mock EquipmentService equipmentService;
  @Mock AwsService awsService;
  @Mock ModelService modelService;
  @Mock CarMapper carMapper;
  @Mock CarSpecification carSpecification;

  @InjectMocks CarServiceImpl carService;

  @Test
  void getAllCars() {
    final var firstCar = Car.builder().id(34L).vin("25654985656").build();
    final var secondCar = Car.builder().id(56L).vin("1545542").build();
    final Page<Car> cars = new PageImpl<>(List.of(firstCar, secondCar));

    final var firstCarDto =
        new CarDTO(
            34L,
            null,
            null,
            2007,
            "photo.jpg",
            "25654985656",
            20067,
            true,
            null,
            null,
            true,
            new BigDecimal("13213.34"),
            null,
            null);
    final var secondCarDto =
        new CarDTO(
            56L,
            null,
            null,
            2007,
            "photo.jpg",
            "1545542",
            20067,
            true,
            null,
            null,
            true,
            new BigDecimal("13213.34"),
            null,
            null);
    final Page<CarDTO> expected = new PageImpl<>(List.of(firstCarDto, secondCarDto));

    final int page = 0;
    final int size = 3;

    given(carMapper.map(firstCar)).willReturn(firstCarDto);
    given(carMapper.map(secondCar)).willReturn(secondCarDto);
    given(carRepository.findAll(carSpecification.getFilter(any()), of(page, size)))
        .willReturn(cars);

    final var actual = carService.getAllCarsOrFindByFilter(any(), page, size);
    assertThat(actual, is(expected));

    then(carMapper).should(times(1)).map(firstCar);
    then(carMapper).should(times(1)).map(secondCar);
    then(carRepository).should(only()).findAll(carSpecification.getFilter(any()), of(page, size));
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
    final var car =
        Car.builder()
            .year(request.getYear())
            .mileage(request.getMileage())
            .price(request.getPrice())
            .vin(request.getVin())
            .build();
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
    given(carRepository.save(any(Car.class))).willReturn(savedCar);

    final var actual = carService.addNewCar(clientId, request);
    assertThat(actual, is(savedCar.getId()));

    then(carMapper).should(only()).map(refEq(request));
    then(modelService).should(only()).getModelById(argThat(modelId::equals));
    then(clientService).should(only()).getClientById(argThat(clientId::equals));
    then(equipmentService).should(only()).getEquipmentById(argThat(equipmentId::equals));
    then(carRepository).should(only()).save(any(Car.class));
  }

  @Test
  void findCarById() {
    final Long id = 1L;
    final var car = Car.builder().id(id).vin("25654985656").build();

    given(carRepository.findById(id)).willReturn(Optional.of(car));

    final var actual = carService.findCarById(id);
    assertThat(actual, is(car));

    then(carRepository).should(only()).findById(argThat(id::equals));
  }

  @Test
  void getCarDtoById() {
    final Long id = 1L;
    final var car = Car.builder().id(id).vin("25654985656").build();
    final var expected =
        new CarDTO(
            id,
            null,
            null,
            2007,
            "photo.jpg",
            "25654985656",
            20067,
            true,
            null,
            null,
            true,
            new BigDecimal("13213.34"),
            null,
            null);

    given(carRepository.findById(id)).willReturn(Optional.of(car));
    given(carMapper.map(car)).willReturn(expected);

    final var actual = carService.getCarById(id);
    assertThat(actual, is(expected));

    then(carRepository).should(only()).findById(argThat(id::equals));
    then(carMapper).should(only()).map(refEq(car));
  }

  @Test
  void getCarByIncorrectId() {
    final Long id = 55L;
    final var expected = new EntityNotFoundException("car", id);
    final var message = expected.getMessage();

    given(carRepository.findById(id)).willReturn(empty());

    final var actual = assertThrows(EntityNotFoundException.class, () -> carService.getCarById(id));
    assertThat(actual.getMessage(), is(message));

    then(carRepository).should(only()).findById(argThat(id::equals));
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
  void getAllCarsByClientId() {
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
            new CarDTO(
                34L,
                null,
                null,
                2007,
                "photo.jpg",
                "25654985656",
                20067,
                true,
                null,
                null,
                true,
                new BigDecimal("13213.34"),
                null,
                null),
            new CarDTO(
                56L,
                null,
                null,
                2007,
                "photo.jpg",
                "1545542",
                20067,
                true,
                null,
                null,
                true,
                new BigDecimal("13213.34"),
                null,
                null));

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
  void changeCarMileage() {
    final var clientId = 3L;
    final var carId = 23L;
    final var car = Car.builder().id(carId).vin("1234567890987654").mileage(14535).build();
    final var client =
        Client.builder().id(clientId).email("frog34crazy@fast.com").cars(Set.of(car)).build();
    final ChangeMileageRequest mileageRequest = new ChangeMileageRequest(carId, 35346);

    given(clientService.getClientById(clientId)).willReturn(client);

    carService.changeCarMileage(clientId, mileageRequest);

    then(clientService).should(only()).getClientById(clientId);
  }

  @Test
  void changeCarMileageWithIncorrectCarId() {
    final var clientId = 3L;
    final var client =
        Client.builder()
            .id(clientId)
            .email("frog34crazy@fast.com")
            .cars(Set.of(Car.builder().id(3L).build()))
            .build();
    final ChangeMileageRequest mileageRequest = new ChangeMileageRequest(341L, 35346);

    given(clientService.getClientById(clientId)).willReturn(client);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> carService.changeCarMileage(clientId, mileageRequest));
    assertThat(actual.getMessage(), is("Could not find any car with the ID 341."));

    then(clientService).should(only()).getClientById(clientId);
  }

  @Test
  void changeCarMileageWithMileageLowerCurrent() {
    final var clientId = 3L;
    final var carId = 23L;
    final var car = Car.builder().id(carId).vin("1234567890987654").mileage(43421).build();
    final var client =
        Client.builder().id(clientId).email("frog34crazy@fast.com").cars(Set.of(car)).build();
    final ChangeMileageRequest mileageRequest = new ChangeMileageRequest(carId, 35346);

    given(clientService.getClientById(clientId)).willReturn(client);

    final var actual =
        assertThrows(
            IllegalArgumentException.class,
            () -> carService.changeCarMileage(clientId, mileageRequest));
    assertThat(actual.getMessage(), is("Mileage should be greater then 43421"));

    then(clientService).should(only()).getClientById(clientId);
  }
}
