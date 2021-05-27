package com.godeltech.mastery.backend.rest;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.GuaranteeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class GuaranteeControllerUnitTest {

  private static final int EXTENDED_MILEAGE = 150_000;
  private static final int REGULAR_MILEAGE = 100_000;

  @Mock private GuaranteeService guaranteeService;
  @InjectMocks GuaranteeController guaranteeController;

  @Test
  void createGuarantee() {
    final var carId = 2L;
    final var guaranteeId = 4L;
    final var createRequest = new GuaranteeCreateRequest(now(), false);
    final ResponseEntity<Long> expected = new ResponseEntity<>(guaranteeId, CREATED);

    given(guaranteeService.createGuarantee(carId, createRequest)).willReturn(guaranteeId);

    final var actual = guaranteeController.createGuarantee(carId, createRequest);
    assertThat(actual, is(expected));

    then(guaranteeService).should(only()).createGuarantee(eq(carId), eq(createRequest));
  }

  @Test
  void createGuaranteeShouldReturnException() {
    final var carId = 1L;
    final var createRequest = new GuaranteeCreateRequest(now(), true);
    final var expected = new IllegalArgumentException("Guarantee already exist");

    given(guaranteeService.createGuarantee(carId, createRequest)).willThrow(expected);

    final var actual =
        assertThrows(
            IllegalArgumentException.class,
            () -> guaranteeController.createGuarantee(carId, createRequest));
    assertThat(actual.getMessage(), is(expected.getMessage()));

    then(guaranteeService).should(only()).createGuarantee(eq(carId), eq(createRequest));
  }

  @Test
  void getGuarantee() {
    final var carId = 1L;
    final var startDate = now();
    final var guaranteeDTO =
        new GuaranteeDTO(startDate, startDate.plusYears(5), EXTENDED_MILEAGE, true);
    final ResponseEntity<GuaranteeDTO> expected = ok(guaranteeDTO);

    given(guaranteeService.getGuarantee(carId)).willReturn(guaranteeDTO);

    final var actual = guaranteeController.getGuarantee(carId);
    assertThat(actual, is(expected));

    then(guaranteeService).should(only()).getGuarantee(carId);
  }

  @Test
  void getGuaranteeWithIncorrectCarId() {
    final var carId = 3421L;
    final String message = format("Could not find any guarantee with car id = %d", carId);
    final var exception = new EntityNotFoundException(message);

    given(guaranteeService.getGuarantee(carId)).willThrow(exception);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> guaranteeController.getGuarantee(carId));
    assertThat(actual.getMessage(), is(message));

    then(guaranteeService).should(only()).getGuarantee(carId);
  }

  @Test
  void extendedGuarantee() {
    final var carId = 3L;
    final var startDate = now();
    final var guaranteeDTO =
        new GuaranteeDTO(startDate, startDate.plusYears(5), REGULAR_MILEAGE, false);
    final ResponseEntity<GuaranteeDTO> expected = ok(guaranteeDTO);

    given(guaranteeService.extensionGuarantee(carId)).willReturn(guaranteeDTO);

    final var actual = guaranteeController.extendedGuarantee(carId);
    assertThat(actual, is(expected));

    then(guaranteeService).should(only()).extensionGuarantee(carId);
  }

  @Test
  void extendedGuaranteeReturnExceptionGuaranteeNotFound() {
    final var carId = 3421L;
    final var expected = new IllegalArgumentException("Guarantee doesn't exist");

    given(guaranteeService.extensionGuarantee(carId)).willThrow(expected);

    final var actual =
        assertThrows(
            IllegalArgumentException.class, () -> guaranteeController.extendedGuarantee(carId));
    assertThat(actual.getMessage(), is(expected.getMessage()));

    then(guaranteeService).should(only()).extensionGuarantee(eq(carId));
  }

  @Test
  void extendedGuaranteeReturnExceptionGuaranteeIsExtended() {
    final var carId = 1L;
    final var expected = new IllegalArgumentException("Guarantee already extended");

    given(guaranteeService.extensionGuarantee(carId)).willThrow(expected);

    final var actual =
        assertThrows(
            IllegalArgumentException.class, () -> guaranteeController.extendedGuarantee(carId));
    assertThat(actual.getMessage(), is(expected.getMessage()));

    then(guaranteeService).should(only()).extensionGuarantee(eq(carId));
  }
}
