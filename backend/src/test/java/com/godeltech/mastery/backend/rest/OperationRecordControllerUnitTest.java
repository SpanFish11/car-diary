package com.godeltech.mastery.backend.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.HttpStatus.CREATED;

import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.OperationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OperationRecordControllerUnitTest {

  @Mock private OperationService operationService;

  @InjectMocks private OperationRecordController operationRecordController;

  @Test
  void saveOperationReturnId() {
    final Long carId = 3L;
    final var createRequest = new OperationCreateRequest();
    createRequest.setServiceOperationNumber("Some service work");
    final Long recordId = 11L;
    final ResponseEntity<Long> expected = new ResponseEntity<>(recordId, CREATED);

    given(operationService.createOperation(carId, createRequest)).willReturn(recordId);

    final var actual = operationRecordController.saveOperation(carId, createRequest);
    assertThat(actual, is(expected));

    then(operationService).should(only()).createOperation(eq(carId), refEq(createRequest));
  }

  @Test
  void saveOperationReturnException() {
    final Long carId = 434L;
    final var createRequest = new OperationCreateRequest();
    createRequest.setServiceOperationNumber("Some service work");
    final var exception = new EntityNotFoundException("car", carId);

    given(operationService.createOperation(carId, createRequest)).willThrow(exception);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> operationRecordController.saveOperation(carId, createRequest));
    assertThat(actual.getMessage(), is(exception.getMessage()));

    then(operationService).should(only()).createOperation(eq(carId), refEq(createRequest));
  }

  @Test
  void getCarOperationsReturnList() {}

  @Test
  void getCarOperationsReturnException() {}

  @Test
  void printCarOperationReturnInputStreamResource() {}

  @Test
  void printCarOperationReturnException() {}
}
