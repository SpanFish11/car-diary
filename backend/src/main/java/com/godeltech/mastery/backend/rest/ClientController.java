package com.godeltech.mastery.backend.rest;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ChangeMileageRequest;
import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ResetPasswordRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.service.AppointmentService;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Client Controller", description = "Operations about client")
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;
  private final CarService carService;
  private final AppointmentService appointmentService;

  @Operation(
      summary = "Get all clients",
      description = "Endpoint for getting all clients",
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
  public ResponseEntity<List<ClientDTO>> getAllClients() {
    return ok(clientService.getAllClients());
  }

  @Operation(
      summary = "Get all client appointments",
      description = "Endpoint for getting all client appointments",
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
  @GetMapping("/appointments")
  public ResponseEntity<List<AppointmentDTO>> getAllAppointments(
      final @AuthenticationPrincipal User principal) {
    return ok(appointmentService.getAllAppointments(principal));
  }

  @Operation(
      summary = "Add new client",
      description = """
          Endpoint for added new client. After adding a client,
          he will receive an email with a username and password.
          """,
      responses = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflict",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @PostMapping
  public ResponseEntity<Long> createClient(@RequestBody @Valid final ClientCreateRequest request) {
    return new ResponseEntity<>(clientService.createClient(request), CREATED);
  }

  @Operation(
      summary = "Get client cars",
      description = "Endpoint for getting client cars by client id",
      responses = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @GetMapping("/{client_id}/cars")
  public ResponseEntity<List<CarDTO>> getClientsCars(
      @PathVariable("client_id") @Min(1) final Long id) {
    return ok(carService.getAllCarsByClientId(id));
  }

  @Operation(
      summary = "Add new car client",
      description = "Endpoint for added new client car",
      responses = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Conflict",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @PostMapping("/{client_id}/cars")
  public ResponseEntity<Long> addCar(
      @PathVariable("client_id") @Min(1) final Long id,
      @RequestBody @Valid final CarCreateRequest request) {
    return new ResponseEntity<>(carService.addNewCar(id, request), CREATED);
  }

  @PatchMapping("/{client_id}/cars")
  public ResponseEntity<HttpStatus> updateCarMileage(
      @PathVariable("client_id") @Min(1) final Long id,
      @RequestBody @Valid ChangeMileageRequest request) {
    carService.changeCarMileage(id, request);
    return ok().build();
  }

  @PutMapping("/password/reset")
  public ResponseEntity<HttpStatus> changePassword(
      @RequestBody @Valid final ResetPasswordRequest request,
      final @AuthenticationPrincipal User principal) {
    clientService.changePassword(request, principal);
    return ok().build();
  }
}
