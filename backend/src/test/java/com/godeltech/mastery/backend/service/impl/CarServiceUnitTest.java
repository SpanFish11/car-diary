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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class CarServiceUnitTest {

  @Mock CarMapper carMapper;
  @Mock AwsService awsService;
  @Mock BrandRepository brandRepository;
  @Mock CarRepository carRepository;

  @InjectMocks CarServiceImpl carService;

  @Test
  void getAllCars() {
    final List<Car> carsExpected =
        List.of(
            Car.builder().vin("25654985656").build(),
            Car.builder().vin("1545542").build(),
            Car.builder().vin("154582654874").build());
    final List<CarDTO> dtoExpected =
        List.of(
            CarDTO.builder().vin("25654985656").build(),
            CarDTO.builder().vin("1545542").build(),
            CarDTO.builder().vin("154582654874").build());

    given(carRepository.findAll()).willReturn(carsExpected);
    given(carMapper.map(carsExpected)).willReturn(dtoExpected);

    final List<CarDTO> actual = carService.getAllCars();
    assertThatObject(List.class, actual, dtoExpected);

    then(carRepository).should(only()).findAll();
    then(carMapper).should(only()).map(refEq(carsExpected));
  }

  @Test
  void getCarBy_given_CorrectId() {
    final Long id = 1L;
    final Car carExpected = Car.builder().id(id).vin("25654985656").build();
    final CarDTO dtoExpected = CarDTO.builder().id(id).vin("25654985656").build();

    given(carRepository.findById(id)).willReturn(Optional.of(carExpected));
    given(carMapper.map(carExpected)).willReturn(dtoExpected);

    final CarDTO actual = carService.getCarById(id);
    assertThatObject(CarDTO.class, actual, dtoExpected);

    then(carRepository).should(only()).findById(argThat(id::equals));
    then(carMapper).should(only()).map(refEq(carExpected));
  }

  @Test
  void getCarBy_given_InvalidId() {
    final Long id = 55L;
    final EntityNotFoundException expected = new EntityNotFoundException("car", id);
    final String message = expected.getMessage();

    given(carRepository.findById(id)).willThrow(expected);

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> carService.getCarById(id));
    assertThat(actual.getMessage(), is(message));

    then(carRepository).should(only()).findById(argThat(id::equals));
  }

  @Test
  void addNewCar() {
    final Long id = 2L;
    final Long expected = 1L;
    final CarCreateRequest request =
        CarCreateRequest.builder().brandId(id).modelId(id).vin("5454544848").build();
    final Brand brand = new Brand(id, "Nissan", Set.of(new Model(id, "Murano", new Brand())));
    final Car saveCarExpected = Car.builder().vin("5454544848").build();
    final Car car = saveCarExpected.toBuilder().id(expected).build();

    given(brandRepository.findById(id)).willReturn(Optional.of(brand));
    given(carMapper.map(request)).willReturn(saveCarExpected);
    given(carRepository.save(saveCarExpected)).willReturn(car);

    final Long actual = carService.addNewCar(request);
    assertThatObject(Long.class, actual, expected);

    then(brandRepository).should(only()).findById(argThat(id::equals));
    then(carMapper).should(only()).map(refEq(request));
    then(carRepository).should(only()).save(argThat(saveCarExpected::equals));
  }

  @Test
  void addNewCar_given_InvalidBrandId() {
    final Long id = 500L;
    final CarCreateRequest request =
        CarCreateRequest.builder().brandId(id).modelId(id).vin("5454544848").build();
    final EntityNotFoundException expected = new EntityNotFoundException("brand", id);
    final String message = expected.getMessage();

    given(brandRepository.findById(id)).willThrow(expected);

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> carService.addNewCar(request));
    assertThat(actual.getMessage(), is(message));

    then(brandRepository).should(only()).findById(argThat(id::equals));
  }

  @Test
  void addNewCar_given_InvalidModelId() {
    final Long modelId = 99L;
    final Long brandId = 1L;
    final CarCreateRequest request =
        CarCreateRequest.builder().brandId(brandId).modelId(modelId).vin("5454544848").build();
    final Brand brand = new Brand(brandId, "Nissan", Set.of(new Model(1L, "Murano", new Brand())));
    final EntityNotFoundException expected = new EntityNotFoundException("model", modelId);
    final String message = expected.getMessage();

    given(brandRepository.findById(brandId)).willReturn(Optional.of(brand));

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> carService.addNewCar(request));
    assertThat(actual.getMessage(), is(message));

    then(brandRepository).should(only()).findById(argThat(brandId::equals));
  }

  @Test
  void updateCarPhoto() {
    final Long id = 1L;
    final MultipartFile multipartFile =
        new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    final String url = "some url";
    final Car carExpected = Car.builder().id(id).vin("25654985656").build();
    final Car car = Car.builder().id(id).photoUrl(url).vin("5454544848").build();

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
  void updateCarPhoto_given_invalidId() {
    final Long id = 60L;
    final MultipartFile multipartFile =
        new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    final String url = "some url";
    final EntityNotFoundException expected = new EntityNotFoundException("car", id);
    final String message = expected.getMessage();

    given(carRepository.findById(id)).willThrow(expected);
    given(awsService.uploadImage(multipartFile, id)).willReturn(url);

    final EntityNotFoundException actual =
        assertThrows(
            EntityNotFoundException.class, () -> carService.updateCarPhoto(id, multipartFile));
    assertThat(actual.getMessage(), is(message));

    then(carRepository).should(only()).findById(argThat(id::equals));
  }

  @Test
  void updateCarPhoto_given_imageButAwsThrowException() {
    final Long id = 1L;
    final Car car = Car.builder().id(id).build();
    final MultipartFile multipartFile =
        new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());
    final RuntimeException expected =
        new RuntimeException("Something went wrong while uploading a picture");
    final String message = expected.getMessage();

    given(carRepository.findById(id)).willReturn(Optional.of(car));
    given(awsService.uploadImage(multipartFile, id)).willThrow(expected);

    final RuntimeException actual =
        assertThrows(RuntimeException.class, () -> carService.updateCarPhoto(id, multipartFile));
    assertThat(actual.getMessage(), is(message));

    then(awsService)
        .should(only())
        .uploadImage(argThat(multipartFile::equals), argThat(id::equals));
  }

  private <T> void assertThatObject(final Class<T> clazz, final T actual, final T expected) {
    assertThat(actual, isA(clazz));
    assertThat(actual, is(expected));
  }
}
