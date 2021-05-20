//package com.godeltech.mastery.backend.rest;
//
//import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
//import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
//import com.godeltech.mastery.backend.exception.EntityNotFoundException;
//import com.godeltech.mastery.backend.service.CarService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.nullValue;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.mockito.BDDMockito.willThrow;
//import static org.mockito.Mockito.only;
//import static org.springframework.http.HttpStatus.CREATED;
//import static org.springframework.http.HttpStatus.OK;
//import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
//import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
//import static org.springframework.http.ResponseEntity.ok;
//
//@ExtendWith(SpringExtension.class)
//class CarControllerUnitTest {
//
//  @Mock CarService carService;
//
//  @InjectMocks CarController carController;
//
//  @Test
//  void getAllCars() {
//    final Page<CarDTO> expected =
//        new PageImpl<>(
//            List.of(
//                CarDTO.builder().vin("4445488552").build(),
//                CarDTO.builder().vin("5694523346554").build(),
//                CarDTO.builder().vin("56985421655").build()));
//
//    given(carService.getAllCarsOrFindByFilter(null, 0, 3)).willReturn(expected);
//
//    final ResponseEntity<Page<CarDTO>> actual = carController.getAllCars(0, 3, null);
//    assertThat(actual, is(ok(expected)));
//
//    then(carService).should(only()).getAllCarsOrFindByFilter(eq(null), eq(0), eq(3));
//  }
//
//  @Test
//  void getCarById_givenId_shouldReturn_Car() {
//    final Long id = 1L;
//    final var car = CarDTO.builder().vin("545587454").build();
//    final ResponseEntity<CarDTO> expected = ok(car);
//
//    given(carService.getCarById(id)).willReturn(car);
//
//    final ResponseEntity<CarDTO> actual = carController.getCarById(id);
//    assertThat(actual, is(expected));
//
//    then(carService).should(only()).getCarById(argThat(id::equals));
//  }
//
//  @Test
//  void getCarById_givenId_shouldThrowException_carNotFound() {
//    final Long id = 2L;
//    final var exception = new EntityNotFoundException("car", id);
//    final var message = exception.getMessage();
//
//    given(carService.getCarById(id)).willThrow(exception);
//
//    final var actual =
//        assertThrows(EntityNotFoundException.class, () -> carController.getCarById(id));
//    assertThat(actual.getMessage(), is(message));
//
//    then(carService).should(only()).getCarById(argThat(id::equals));
//  }
//
//  @Test
//  void createCar_givenRequest_shouldReturnId() {
//    final var request = CarCreateManagerRequest.managerRequestBuilder().build();
//    final Long savedCarId = 1L;
//    final ResponseEntity<Long> expected = new ResponseEntity<>(savedCarId, CREATED);
//
//    given(carService.addNewCar(request)).willReturn(savedCarId);
//
//    final ResponseEntity<Long> actual = carController.createCar(request);
//    assertThat(actual, is(expected));
//
//    then(carService).should(only()).addNewCar(argThat(request::equals));
//  }
//
//  @Test
//  void createCar_givenRequest_shouldThrowException_clientNotFound() {
//    final Long clientId = 1L;
//    final var request = CarCreateManagerRequest.managerRequestBuilder().clientId(clientId).build();
//    final var exception = new EntityNotFoundException("client", clientId);
//    final var message = exception.getMessage();
//
//    given(carService.addNewCar(request)).willThrow(exception);
//
//    final var actual =
//        assertThrows(EntityNotFoundException.class, () -> carController.createCar(request));
//    assertThat(actual.getMessage(), is(message));
//
//    then(carService).should(only()).addNewCar(argThat(request::equals));
//  }
//
//  @Test
//  void createCar_givenRequest_shouldThrowException_modelNotFound() {
//    final Long modelId = 1L;
//    final var request = CarCreateManagerRequest.managerRequestBuilder().modelId(modelId).build();
//    final var exception = new EntityNotFoundException("model", modelId);
//    final var message = exception.getMessage();
//
//    given(carService.addNewCar(request)).willThrow(exception);
//
//    final var actual =
//        assertThrows(EntityNotFoundException.class, () -> carController.createCar(request));
//    assertThat(actual.getMessage(), is(message));
//
//    then(carService).should(only()).addNewCar(argThat(request::equals));
//  }
//
//  @Test
//  void createCar_givenRequest_shouldThrowException_equipmentNotFound() {
//    final Long equipmentId = 1L;
//    final var request =
//        CarCreateManagerRequest.managerRequestBuilder().equipmentId(equipmentId).build();
//    final var exception = new EntityNotFoundException("equipment", equipmentId);
//    final var message = exception.getMessage();
//
//    given(carService.addNewCar(request)).willThrow(exception);
//
//    final var actual =
//        assertThrows(EntityNotFoundException.class, () -> carController.createCar(request));
//    assertThat(actual.getMessage(), is(message));
//
//    then(carService).should(only()).addNewCar(argThat(request::equals));
//  }
//
//  @Test
//  void updateCarPhoto() throws HttpMediaTypeNotSupportedException {
//    final Long id = 1L;
//    final var multipartFile =
//        new MockMultipartFile(
//            "photo", "sourceFile.jpeg", IMAGE_JPEG_VALUE, "Hello World".getBytes());
//
//    willDoNothing().given(carService).updateCarPhoto(id, multipartFile);
//
//    final ResponseEntity<HttpStatus> actual = carController.updateCarPhoto(id, multipartFile);
//    assertThat(actual.getBody(), nullValue());
//    assertThat(actual.getStatusCode(), is(OK));
//
//    then(carService)
//        .should(only())
//        .updateCarPhoto(argThat(id::equals), argThat(multipartFile::equals));
//  }
//
//  @Test
//  void updateCarPhoto_invalidMediaType() {
//    final Long id = 1L;
//    final var expected =
//        new HttpMediaTypeNotSupportedException("Content type 'multipart/form-data' not supported");
//    final String message = expected.getMessage();
//    final var multipartFile =
//        new MockMultipartFile(
//            "photo", "sourceFile.tmp", MULTIPART_FORM_DATA_VALUE, "Hello World".getBytes());
//
//    willDoNothing().given(carService).updateCarPhoto(id, multipartFile);
//
//    final var actual =
//        assertThrows(
//            HttpMediaTypeNotSupportedException.class,
//            () -> carController.updateCarPhoto(id, multipartFile));
//
//    assertThat(actual.getMessage(), is(message));
//  }
//
//  @Test
//  void updateCarPhoto_givenId_shouldThrow_carNotFound() {
//    final Long id = 1L;
//    final var multipartFile =
//        new MockMultipartFile(
//            "photo", "sourceFile.jpeg", IMAGE_JPEG_VALUE, "Hello World".getBytes());
//    final var exception = new EntityNotFoundException("car", id);
//    final var message = exception.getMessage();
//
//    willThrow(exception).given(carService).updateCarPhoto(id, multipartFile);
//
//    final var actual =
//        assertThrows(
//            EntityNotFoundException.class, () -> carController.updateCarPhoto(id, multipartFile));
//    assertThat(actual.getMessage(), is(message));
//
//    then(carService)
//        .should(only())
//        .updateCarPhoto(argThat(id::equals), argThat(multipartFile::equals));
//  }
//}
