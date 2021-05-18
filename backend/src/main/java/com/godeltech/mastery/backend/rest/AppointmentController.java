package com.godeltech.mastery.backend.rest;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.domain.entity.AppointmentStatus;
import com.godeltech.mastery.backend.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Appointment Controller", description = "Operations with appointment")
@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

  private final AppointmentService appointmentService;

  @Operation(
      summary = "Get all appointments",
      description = "Endpoint for getting all appointments",
      responses = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @GetMapping
  public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
    return ok(appointmentService.getAllAppointments());
  }

  @PostMapping
  public ResponseEntity<Long> createAppointment(
      @RequestBody @Valid final AppointmentCreateRequest createRequest,
      final Authentication principal) {
    return new ResponseEntity<>(
        appointmentService.createAppointment(createRequest, principal), CREATED);
  }

  @PutMapping("/{appointment_id}")
  public ResponseEntity<AppointmentDTO> changeStatus(
      @PathVariable("appointment_id") final Long appointmentId,
      @RequestParam final AppointmentStatus status) {
    return ok(appointmentService.changeAppointmentStatus(appointmentId, status));
  }
}
