package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@ExtendWith(SpringExtension.class)
class ClientControllerUnitTest {

  @Mock private ClientService clientService;
  @Mock private CarService carService;

  @InjectMocks ClientController clientController;

  @Test
  void getAllClients() {
    final var clients = List.of(new ClientDTO(2L, "John", "Snow", "snow324@mail.com"));
    final ResponseEntity<List<ClientDTO>> expected = ok(clients);

    given(clientService.getAllClients()).willReturn(clients);

    final ResponseEntity<List<ClientDTO>> actual = clientController.getAllClients();
    assertThat(actual, is(expected));

    then(clientService).should(only()).getAllClients();
  }

  @Test
  void createClient_given_uniqueEmail_shouldCreate() {
    final var request =
        ClientCreateRequest.builder()
            .firstName("Spencer")
            .lastName("Haynes")
            .email("shepaj53378@iludir.com")
            .build();
    final Long savedClientId = 5L;
    final ResponseEntity<Long> expected = new ResponseEntity<>(savedClientId, CREATED);

    given(clientService.createClient(request)).willReturn(savedClientId);

    final ResponseEntity<Long> actual = clientController.createClient(request);
    assertThat(actual, is(expected));

    then(clientService).should(only()).createClient(argThat(request::equals));
  }

  @Test
  void createClient_given_notUniqueEmail_shouldTrowException() {
    final var request =
        ClientCreateRequest.builder()
            .firstName("Spencer")
            .lastName("Haynes")
            .email("shepaj53378@iludir.com")
            .build();
    final var exception =
        new IllegalArgumentException(format("Email %s already exists", request.getEmail()));
    final var message = exception.getMessage();

    given(clientService.createClient(request)).willThrow(exception);

    final var actual =
        assertThrows(IllegalArgumentException.class, () -> clientController.createClient(request));
    assertThat(actual.getMessage(), is(message));

    then(clientService).should(only()).createClient(argThat(request::equals));
  }

  @Test
  void getClientsCars_givenId_shouldReturn_cars() {
    final var client = new ClientDTO(2L, "John", "Snow", "snow324@mail.com");
    final var cars =
        List.of(
            CarDTO.builder().vin("4S3BMHB68B3286050").client(client).build(),
            CarDTO.builder().vin("4Y1SL65848Z411439").client(client).build());
    final ResponseEntity<List<CarDTO>> expected = ok(cars);

    given(carService.getAllCarsByClientId(client.getId())).willReturn(cars);

    final ResponseEntity<List<CarDTO>> actual = clientController.getClientsCars(client.getId());
    assertThat(actual, is(expected));

    then(carService).should(only()).getAllCarsByClientId(argThat(client.getId()::equals));
  }

  @Test
  void getClientsCars_givenId_should_ThrowException_notFoundClient() {
    final Long id = 5434L;
    final var exception = new EntityNotFoundException("client", id);
    final var message = exception.getMessage();

    given(carService.getAllCarsByClientId(id)).willThrow(exception);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> clientController.getClientsCars(id));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).getAllCarsByClientId(argThat(id::equals));
  }

  @Test
  void addCar_givenIdAndRequest_shouldReturnCarId_And_statusCreated() {
    final Long clientId = 1L;
    final var request =
        CarCreateRequest.builder()
            .modelId(1L)
            .year(2020)
            .vin("12345D78901234567")
            .mileage(20)
            .price(new BigDecimal("20"))
            .equipmentId(1L)
            .build();
    final Long createdCarId = 2L;
    final ResponseEntity<Long> expected = new ResponseEntity<>(createdCarId, CREATED);

    given(carService.addNewCar(clientId, request)).willReturn(createdCarId);

    final ResponseEntity<Long> actual = clientController.addCar(clientId, request);
    assertThat(actual, is(expected));

    then(carService).should(only()).addNewCar(argThat(clientId::equals), argThat(request::equals));
  }

  @Test
  void addCar_givenIdAndRequest_shouldTrow_clientNotFoundException() {
    final Long clientId = 5545L;
    final var request = CarCreateRequest.builder().modelId(5L).equipmentId(5L).build();
    final var exception = new EntityNotFoundException("client", clientId);
    final var message = exception.getMessage();

    given(carService.addNewCar(clientId, request)).willThrow(exception);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> clientController.addCar(clientId, request));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).addNewCar(argThat(clientId::equals), argThat(request::equals));
  }

  @Test
  void addCar_givenIdAndRequest_shouldTrow_modelNotFoundException() {
    final Long clientId = 1L;
    final var request = CarCreateRequest.builder().modelId(65565L).equipmentId(5L).build();
    final var exception = new EntityNotFoundException("model", request.getModelId());
    final var message = exception.getMessage();

    given(carService.addNewCar(clientId, request)).willThrow(exception);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> clientController.addCar(clientId, request));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).addNewCar(argThat(clientId::equals), argThat(request::equals));
  }

  @Test
  void addCar_givenIdAndRequest_shouldTrow_equipmentNotFoundException() {
    final Long clientId = 1L;
    final var request = CarCreateRequest.builder().modelId(1L).equipmentId(6646L).build();
    final var exception = new EntityNotFoundException("equipment", request.getEquipmentId());
    final var message = exception.getMessage();

    given(carService.addNewCar(clientId, request)).willThrow(exception);

    final var actual =
            assertThrows(
                    EntityNotFoundException.class, () -> clientController.addCar(clientId, request));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).addNewCar(argThat(clientId::equals), argThat(request::equals));
  }
}
