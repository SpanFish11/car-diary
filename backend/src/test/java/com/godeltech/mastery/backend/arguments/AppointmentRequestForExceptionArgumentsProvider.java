package com.godeltech.mastery.backend.arguments;

import static java.lang.Boolean.TRUE;
import static java.time.LocalDate.now;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class AppointmentRequestForExceptionArgumentsProvider implements ArgumentsProvider {

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    return Stream.of(Arguments.of(new AppointmentCreateRequest(null, 1L, "Description", 1L, now()),
        "repairment is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 0L, "Description", 1L, now()),
            "Value should be greater then or equal to 1"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, null, 1L, now()),
            "description is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", null, now()),
            "car is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", 0L, now()),
            "Value should be greater then or equal to 1"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", 1L, null),
            "Appointment date is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", 1L, now().minusDays(1)),
            "Appointment date should be in the present of future")
    );
  }
}
