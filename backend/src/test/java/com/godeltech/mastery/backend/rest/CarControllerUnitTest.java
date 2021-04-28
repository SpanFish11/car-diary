package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.only;
import static org.mockito.quality.Strictness.LENIENT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class CarControllerUnitTest {

  @Mock CarService carService;

  @InjectMocks CarController carController;

  @Test
  void getAllCars() {
    final List<CarDTO> expected =
        List.of(
            CarDTO.builder().vin("4445488552").build(),
            CarDTO.builder().vin("5694523346554").build(),
            CarDTO.builder().vin("56985421655").build());

    given(carService.getAllCars()).willReturn(expected);

    final ResponseEntity<List<CarDTO>> actual = carController.getAllCars();
    assertThatStatusCodeAndBodyAndClass(actual, expected, OK, List.class);

    then(carService).should(only()).getAllCars();
  }

  @Test
  void getCarById() {
    final Long id = 1L;
    final CarDTO expected = CarDTO.builder().vin("545587454").build();

    given(carService.getCarById(id)).willReturn(expected);

    final ResponseEntity<CarDTO> actual = carController.getCarById(id);
    assertThatStatusCodeAndBodyAndClass(actual, expected, OK, CarDTO.class);

    then(carService).should(only()).getCarById(argThat(id::equals));
  }

  @Test
  void createCar() {
    final CarCreateRequest request = CarCreateRequest.builder().build();
    final Long expected = 1L;

    given(carService.addNewCar(request)).willReturn(expected);

    final ResponseEntity<Long> actual = carController.createCar(request);
    assertThatStatusCodeAndBodyAndClass(actual, expected, CREATED, Long.class);

    then(carService).should(only()).addNewCar(argThat(request::equals));
  }

  @Test
  void updateCarPhoto() throws HttpMediaTypeNotSupportedException {
    final Long id = 1L;
    final MultipartFile multipartFile =
        new MockMultipartFile(
            "photo", "sourceFile.jpeg", IMAGE_JPEG_VALUE, "Hello World".getBytes());

    willDoNothing().given(carService).updateCarPhoto(id, multipartFile);

    final ResponseEntity<HttpStatus> actual = carController.updateCarPhoto(id, multipartFile);
    assertThat(actual.getBody(), nullValue());
    assertThat(actual.getStatusCode(), is(OK));

    then(carService)
        .should(only())
        .updateCarPhoto(argThat(id::equals), argThat(multipartFile::equals));
  }

  @Test
  void updateCarPhoto_invalidMediaType() {
    final Long id = 1L;
    final var expected =
        new HttpMediaTypeNotSupportedException("Content type 'multipart/form-data' not supported");
    final String message = expected.getMessage();
    final var multipartFile =
        new MockMultipartFile(
            "photo", "sourceFile.tmp", MULTIPART_FORM_DATA_VALUE, "Hello World".getBytes());

    willDoNothing().given(carService).updateCarPhoto(id, multipartFile);

    final HttpMediaTypeNotSupportedException actual =
        assertThrows(
            HttpMediaTypeNotSupportedException.class,
            () -> carController.updateCarPhoto(id, multipartFile));

    assertThat(actual.getMessage(), is(message));
  }

  private <T> void assertThatStatusCodeAndBodyAndClass(
      final ResponseEntity<T> actual,
      final Object object,
      final HttpStatus status,
      final Class<?> clazz) {
    assertThat(actual.getBody(), isA(clazz));
    assertThat(actual.getBody(), is(object));
    assertThat(actual.getStatusCode(), is(status));
  }
}
