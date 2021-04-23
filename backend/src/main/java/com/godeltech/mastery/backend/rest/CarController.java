package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Car Controller", description = "Operations about car")
@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

  private final CarService carService;

  @Operation(summary = "Get all cars", description = "Endpoint for getting all cars")
  @GetMapping
  public ResponseEntity<List<CarDTO>> getAllCars() {
    return ok(carService.getAllCars());
  }

  @Operation(summary = "Get car by id", description = "Endpoint for getting car by id")
  @GetMapping("/{car_id}")
  public ResponseEntity<CarDTO> getCarById(@PathVariable("car_id") @Min(1) final Long id) {
    return ok(carService.getCarById(id));
  }

  @Operation(summary = "Add new car", description = "Endpoint for added new car")
  @PostMapping
  public ResponseEntity<Long> createCar(@RequestBody @Valid final CarCreateRequest request) {
    return new ResponseEntity<>(carService.addNewCar(request), CREATED);
  }

  @Operation(summary = "Update car photo", description = "Endpoint for adding a photo for a car")
  @PatchMapping("/{car_id}/photos")
  public ResponseEntity<HttpStatus> updateCarPhoto(
      @PathVariable("car_id") @Min(1) final Long id,
      @RequestPart(value = "photo") final MultipartFile photo) {
    carService.updateCarPhoto(id, photo);
    return ok().build();
  }
}
