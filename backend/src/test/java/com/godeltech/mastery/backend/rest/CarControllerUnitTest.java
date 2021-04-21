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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.only;
import static org.mockito.quality.Strictness.LENIENT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    assertThat(actual.getBody(), isA(List.class));
    assertThat(actual.getBody(), hasSize(3));
    assertThat(actual.getBody(), is(expected));
    assertThat(actual.getStatusCode(), is(OK));

    then(carService).should(only()).getAllCars();
  }

  @Test
  void getCarById() {
    final Long id = 1L;
    final CarDTO expected = CarDTO.builder().vin("545587454").build();

    given(carService.getCarById(id)).willReturn(expected);

    final ResponseEntity<CarDTO> actual = carController.getCarById(id);
    assertThat(actual.getBody(), isA(CarDTO.class));
    assertThat(actual.getBody(), is(expected));
    assertThat(actual.getStatusCode(), is(OK));

    then(carService).should(only()).getCarById(argThat(id::equals));
  }

  @Test
  void createCar() {
    final CarCreateRequest request = CarCreateRequest.builder().build();

    willDoNothing().given(carService).addNewCar(request);

    final ResponseEntity<HttpStatus> actual = carController.createCar(request);
    assertThat(actual.getBody(), nullValue());
    assertThat(actual.getStatusCode(), is(CREATED));

    then(carService).should(only()).addNewCar(argThat(request::equals));
  }

  @Test
  void updateCarPhoto() {
    final Long id = 1L;
    final MultipartFile multipartFile =
        new MockMultipartFile("sourceFile.tmp", "Hello World".getBytes());

    willDoNothing().given(carService).updateCarPhoto(id, multipartFile);

    final ResponseEntity<HttpStatus> actual = carController.updateCarPhoto(id, multipartFile);
    assertThat(actual.getBody(), nullValue());
    assertThat(actual.getStatusCode(), is(OK));

    then(carService)
        .should(only())
        .updateCarPhoto(argThat(id::equals), argThat(multipartFile::equals));
  }
}
