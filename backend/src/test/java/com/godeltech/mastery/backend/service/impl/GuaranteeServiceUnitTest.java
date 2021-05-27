package com.godeltech.mastery.backend.service.impl;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Guarantee;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.GuaranteeMapper;
import com.godeltech.mastery.backend.repository.GuaranteeRepository;
import com.godeltech.mastery.backend.service.CarService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GuaranteeServiceUnitTest {

  @Mock GuaranteeMapper guaranteeMapper;
  @Mock GuaranteeRepository guaranteeRepository;
  @Mock CarService carService;

  @InjectMocks GuaranteeServiceImpl guaranteeService;

  @Test
  void createGuarantee() {
    final var carId = 1L;
    final var car = Car.builder().id(carId).vin("ASW21DE45GF5F4D23").build();
    final var createRequest = new GuaranteeCreateRequest(LocalDate.now(), false);
    Guarantee guarantee =
        Guarantee.builder()
            .start(createRequest.getStart())
            .extended(createRequest.getExtended())
            .build();
    Guarantee excepted =
        Guarantee.builder()
            .id(3L)
            .start(createRequest.getStart())
            .extended(createRequest.getExtended())
            .build();

    given(carService.findCarById(carId)).willReturn(car);
    given(guaranteeMapper.toEntity(createRequest)).willReturn(guarantee);
    given(guaranteeRepository.save(guarantee)).willReturn(excepted);

    final var actual = guaranteeService.createGuarantee(carId, createRequest);
    assertThat(actual, is(excepted.getId()));

    then(carService).should(only()).findCarById(eq(carId));
    then(guaranteeMapper).should(only()).toEntity(refEq(createRequest));
    then(guaranteeRepository).should(only()).save(guarantee);
  }

  @Test
  void createGuaranteeIfGuaranteeExist() {
    final var carId = 1L;
    final var car =
        Car.builder().id(carId).vin("ASW21DE45GF5F4D23").guarantee(new Guarantee()).build();
    final var message = "Guarantee already exist";

    given(carService.findCarById(carId)).willReturn(car);

    final var actual =
        assertThrows(
            IllegalArgumentException.class,
            () -> guaranteeService.createGuarantee(carId, new GuaranteeCreateRequest()));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).findCarById(eq(carId));
  }

  @Test
  void getGuaranteeWithCorrectCarId() {
    final var carId = 1L;
    final var car = Car.builder().id(carId).vin("ASW21DE45GF5F4D23").build();
    final var guarantee = new Guarantee();
    final var expected =
        new GuaranteeDTO(
            LocalDate.parse("2021-03-29"), LocalDate.parse("2026-03-29"), 150_000, true);

    given(carService.findCarById(carId)).willReturn(car);
    given(guaranteeRepository.findByCar(car)).willReturn(Optional.of(guarantee));
    given(guaranteeMapper.toDTO(guarantee)).willReturn(expected);

    final var actual = guaranteeService.getGuarantee(carId);
    assertThat(actual, is(expected));

    then(carService).should(only()).findCarById(eq(carId));
    then(guaranteeRepository).should(only()).findByCar(refEq(car));
    then(guaranteeMapper).should(only()).toDTO(refEq(guarantee));
  }

  @Test
  void getGuaranteeFromCarWithoutGuarantee() {
    final var carId = 568L;
    final var car = Car.builder().id(carId).vin("ASW21DE45GF5F4D23").build();
    final var message = format("Could not find any guarantee with car id = %d", carId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(carService.findCarById(carId)).willReturn(car);
    given(guaranteeRepository.findByCar(car)).willThrow(excepted);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> guaranteeService.getGuarantee(carId));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).findCarById(eq(carId));
    then(guaranteeRepository).should(only()).findByCar(refEq(car));
  }

  @Test
  void extensionGuarantee() {
    final var carId = 1L;
    Guarantee guarantee =
        Guarantee.builder().start(LocalDate.parse("2021-03-29")).extended(false).build();
    final var expected =
        new GuaranteeDTO(
            LocalDate.parse("2021-03-29"), LocalDate.parse("2026-03-29"), 150_000, true);
    final var car = Car.builder().id(carId).vin("ASW21DE45GF5F4D23").guarantee(guarantee).build();

    given(carService.findCarById(carId)).willReturn(car);
    given(guaranteeRepository.save(guarantee)).willReturn(guarantee);
    given(guaranteeMapper.toDTO(guarantee)).willReturn(expected);

    final GuaranteeDTO actual = guaranteeService.extensionGuarantee(carId);
    assertThat(actual, is(expected));

    then(carService).should(only()).findCarById(eq(carId));
    then(guaranteeRepository).should(only()).save(refEq(guarantee));
    then(guaranteeMapper).should(only()).toDTO(refEq(guarantee));
  }

  @Test
  void extensionGuaranteeIfGuaranteeNotExist() {
    final var carId = 1L;
    final var car = Car.builder().id(carId).vin("ASW21DE45GF5F4D23").guarantee(null).build();
    final var message = "Guarantee doesn't exist";

    given(carService.findCarById(carId)).willReturn(car);

    final var actual =
        assertThrows(
            IllegalArgumentException.class, () -> guaranteeService.extensionGuarantee(carId));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).findCarById(eq(carId));
  }

  @Test
  void extensionGuaranteeIfGuaranteeIsExtended() {
    final var carId = 1L;
    Guarantee guarantee = Guarantee.builder().extended(true).build();
    final var car = Car.builder().id(carId).vin("ASW21DE45GF5F4D23").guarantee(guarantee).build();
    final var message = "Guarantee already extended";

    given(carService.findCarById(carId)).willReturn(car);

    final var actual =
        assertThrows(
            IllegalArgumentException.class, () -> guaranteeService.extensionGuarantee(carId));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).findCarById(eq(carId));
  }
}
