package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import com.godeltech.mastery.backend.domain.dto.request.OperationCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

import static java.util.List.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Car Controller", description = "Operations about car")
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

  private final CarService carService;

  @Operation(
      summary = "Get all cars or search by filter",
      description = "Endpoint for getting all cars",
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
  public ResponseEntity<Page<CarDTO>> getAllCars(
      @RequestParam(value = "page", defaultValue = "0", required = false) final Integer page,
      @RequestParam(value = "size", defaultValue = "5", required = false) final Integer size,
      @Valid final Filter filter) {
    return ok(carService.getAllCarsOrFindByFilter(filter, page, size));
  }

  @Operation(
      summary = "Get car by id",
      description = "Endpoint for getting car by id",
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
  @GetMapping("/{car_id}")
  public ResponseEntity<CarDTO> getCarById(@PathVariable("car_id") @Min(1) final Long id) {
    return ok(carService.getCarById(id));
  }

  @Operation(
      summary = "Add new car",
      description = "Endpoint for added new car for client",
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
  public ResponseEntity<Long> createCar(@RequestBody @Valid final CarCreateManagerRequest request) {
    return new ResponseEntity<>(carService.addNewCar(request), CREATED);
  }

  @Operation(
      summary = "Update car photo",
      description = "Endpoint for adding a photo for a car",
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
            responseCode = "415",
            description = "Unsupported Media Type",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ExceptionResponseDTO.class)))
      })
  @PatchMapping("/{car_id}/photos")
  public ResponseEntity<HttpStatus> updateCarPhoto(
      @PathVariable("car_id") @Min(1) final Long id,
      @RequestPart(value = "photo") final MultipartFile photo)
      throws HttpMediaTypeNotSupportedException {
    checkImageMediaType(photo.getContentType());
    carService.updateCarPhoto(id, photo);
    return ok().build();
  }

  @GetMapping("/my")
  public ResponseEntity<List<CarDTO>> getMyCars(final Authentication principal) {
    return ok(carService.getCurrentClientCars(principal));
  }

  private void checkImageMediaType(final String type) throws HttpMediaTypeNotSupportedException {
    if (!Objects.equals(type, IMAGE_JPEG_VALUE) && !Objects.equals(type, IMAGE_PNG_VALUE)) {
      throw new HttpMediaTypeNotSupportedException(parseMediaType(type), of(IMAGE_JPEG, IMAGE_PNG));
    }
  }
}
