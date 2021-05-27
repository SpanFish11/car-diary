package com.godeltech.mastery.backend.service.impl;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceOperationRecordDTO;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.ServiceOperationRecord;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.OperationMapper;
import com.godeltech.mastery.backend.repository.OperationRepository;
import com.godeltech.mastery.backend.service.CarService;
import java.io.File;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OperationServiceUnitTest {

  @Mock OperationRepository operationRepository;
  @Mock CarService carService;
  @Mock OperationMapper operationMapper;

  @InjectMocks OperationServiceImpl operationService;

  @Test
  void createOperation() {
    final var operationCreateRequest = new OperationCreateRequest();
    operationCreateRequest.setDate(now());
    operationCreateRequest.setServiceOperationNumber("TO-full");
    final var serviceOperationRecord = new ServiceOperationRecord();
    serviceOperationRecord.setId(23L);
    serviceOperationRecord.setDate(operationCreateRequest.getDate());
    serviceOperationRecord.setServiceOperationNumber(
        operationCreateRequest.getServiceOperationNumber());
    final var carId = 1L;
    final var car = Car.builder().id(carId).vin("DER12CD61GPA07N45").build();

    given(operationMapper.toEntity(operationCreateRequest)).willReturn(serviceOperationRecord);
    given(carService.findCarById(carId)).willReturn(car);
    given(operationRepository.save(serviceOperationRecord)).willReturn(serviceOperationRecord);

    final Long actual = operationService.createOperation(carId, operationCreateRequest);
    assertThat(actual, is(serviceOperationRecord.getId()));

    then(operationMapper).should(only()).toEntity(operationCreateRequest);
    then(carService).should(only()).findCarById(eq(carId));
    then(operationRepository).should(only()).save(serviceOperationRecord);
  }

  @Test
  void getAllRecordsByCarId() {
    final var carId = 1L;
    final var car = Car.builder().id(carId).vin("DER12CD61GPA07N45").build();
    final List<ServiceOperationRecord> records =
        List.of(new ServiceOperationRecord(2L, "TO-2", now(), 23671, null, null, car));
    final List<ServiceOperationRecordDTO> expected =
        List.of(new ServiceOperationRecordDTO(2L, "TO-2", now(), 23671, null, null));

    given(carService.findCarById(carId)).willReturn(car);
    given(operationRepository.getAllByCar(car)).willReturn(Optional.of(records));
    given(operationMapper.toDTOList(records)).willReturn(expected);

    final List<ServiceOperationRecordDTO> actual = operationService.getAllRecordsByCarId(carId);
    assertThat(actual, is(expected));

    then(carService).should(only()).findCarById(eq(carId));
    then(operationRepository).should(only()).getAllByCar(refEq(car));
    then(operationMapper).should(only()).toDTOList(records);
  }

  @Test
  void getAllRecordsWithIncorrectCarId() {
    final var incorrectCarId = 1345L;
    final var car = Car.builder().id(incorrectCarId).vin("DER12CD61GPA07N45").build();
    final var message =
        format("Could not find any operation records by car id = %d", incorrectCarId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(carService.findCarById(incorrectCarId)).willReturn(car);
    given(operationRepository.getAllByCar(car)).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> operationService.getAllRecordsByCarId(incorrectCarId));
    assertThat(actual.getMessage(), is(message));

    then(carService).should(only()).findCarById(eq(incorrectCarId));
    then(operationRepository).should(only()).getAllByCar(refEq(car));
  }

  @Test
  void createReport() {
    final var carId = 1L;
    final var car = Car.builder().id(carId).vin("DER12CD61GPA07N45").build();
    final var expected = new File("carOperations.pdf");

    given(carService.findCarById(carId)).willReturn(car);

    final var actual = operationService.createReport(carId);
    assertThat(actual, is(expected));

    then(carService).should(only()).findCarById(eq(carId));
  }
}
