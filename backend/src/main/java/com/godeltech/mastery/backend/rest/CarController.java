package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.CarDTO;
import com.godeltech.mastery.backend.service.CarService;
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

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

  private final CarService carService;

  @GetMapping
  public ResponseEntity<List<CarDTO>> getAllCars() {
    return ok(carService.getAllCars());
  }

  @GetMapping("/{car_id}")
  public ResponseEntity<CarDTO> getCarById(@PathVariable("car_id") @Min(1) final Long id) {
    return ok(carService.getCarById(id));
  }

  @PostMapping
  public ResponseEntity<HttpStatus> createCar(@RequestBody @Valid final CarCreateRequest request) {
    carService.addNewCar(request);
    return new ResponseEntity<>(CREATED);
  }

  @PatchMapping("/{car_id}/photos")
  public ResponseEntity<HttpStatus> updateCarPhoto(
      @PathVariable("car_id") @Min(1) final Long id,
      @RequestPart(value = "photo") final MultipartFile photo) {
    carService.updateCarPhoto(id, photo);
    return ok().build();
  }
}
