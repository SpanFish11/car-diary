package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.CarMapper;
import com.godeltech.mastery.backend.repository.CarRepository;
import com.godeltech.mastery.backend.repository.ModelRepository;
import com.godeltech.mastery.backend.service.AwsService;
import com.godeltech.mastery.backend.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/tests/cars/initTestCars.sql")
class CarServiceIntegrationTest {

  @Autowired private CarRepository carRepository;
  @Autowired private ModelRepository modelRepository;
  @Autowired private AwsService awsService;
  @Autowired private CarMapper mapper;

  private CarService carService;

  @BeforeEach
  void setUp() {
    carService = new CarServiceImpl(carRepository, awsService, modelRepository, mapper);
  }

  @Test
  void getAllCars() {
    final var terrano = new ModelDTO(2L, "Terrano");
    final var coolray = new ModelDTO(1L, "Coolray");

    final List<CarDTO> expected =
        List.of(
            CarDTO.builder()
                .id(1L)
                .brand(new BrandDTO(1L, "Nissan", Set.of(terrano)))
                .model(terrano)
                .year(2017)
                .photoUrl("nissan.jpg")
                .vin("4S3BMHB68B3286050")
                .mileage(25405)
                .build(),
            CarDTO.builder()
                .id(2L)
                .brand(new BrandDTO(2L, "Geely", Set.of(coolray)))
                .model(coolray)
                .year(2021)
                .photoUrl("geely.jpg")
                .vin("4Y1SL65848Z411439")
                .mileage(0)
                .build());

    final List<CarDTO> actual = carService.getAllCars();
    assertThat(
        actual,
        is(expected));
  }

  @Test
  void getCarByCorrectId() {
    final var terrano = new ModelDTO(2L, "Terrano");
    final var expected =
        CarDTO.builder()
            .id(1L)
            .brand(new BrandDTO(1L, "Nissan", Set.of(terrano)))
            .model(terrano)
            .year(2017)
            .photoUrl("nissan.jpg")
            .vin("4S3BMHB68B3286050")
            .mileage(25405)
            .build();

    assertThat(carService.getCarById(1L), is(expected));
  }

  @Test
  void getCarByIncorrectId() {
    final Long id = 7L;
    final var message = format("Could not find any car with the ID %d.", id);

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> carService.getCarById(id));
    assertThat(actual.getMessage(), is(message));
  }

  @Test
  void addNewCar() {
    final var carRequest =
        CarCreateRequest.builder()
            .mileage(25405)
            .modelId(2L)
            .vin("4S3BMGB68B3286050")
            .year(2017)
            .build();

    assertThat(carService.addNewCar(carRequest), is(3L));
  }

  @Test
  void addNewCarIncorrectModelId() {
    final var carRequest =
        CarCreateRequest.builder()
            .modelId(9L)
            .year(2017)
            .vin("4S3BMHB68B3286050")
            .mileage(25405)
            .build();
    final var message = "Could not find any model with the ID 9.";

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> carService.addNewCar(carRequest));

    assertThat(actual.getMessage(), is(message));
  }

  @Test
  void updateCarPhoto() {
    final Long carId = 1L;
    final var multipartFile =
        new MockMultipartFile(
            "image.jpg", "image.jpg", MULTIPART_FORM_DATA_VALUE, "Hello World".getBytes());

    carService.updateCarPhoto(carId, multipartFile);

    assertThat(
        carService.getCarById(carId).getPhotoUrl(),
        stringContainsInOrder("https://bucket.s3.region.amazonaws.com/folder/1/", ".jpg"));
  }
}
