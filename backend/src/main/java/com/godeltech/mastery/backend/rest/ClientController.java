package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.request.CarCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

  private final ClientService clientService;
  private final CarService carService;

  @GetMapping
  public ResponseEntity<List<ClientDTO>> getAllClients() {
    return ok(clientService.getAllClients());
  }

  @PostMapping
  public ResponseEntity<Long> createClient(@RequestBody @Valid final ClientCreateRequest request) {
    return ok(clientService.createClient(request));
  }

  @GetMapping("/{client_id}/cars")
  public ResponseEntity<List<CarDTO>> getClientsCars(
      @PathVariable("client_id") @Min(1) final Long id) {
    return ok(carService.getAllCarsByClientId(id));
  }

  @PostMapping("/{client_id}/cars")
  public ResponseEntity<Long> addCar(
      @PathVariable("client_id") @Min(1) final Long id,
      @RequestBody @Valid final CarCreateRequest request) {
    return ok(carService.addNewCar(id, request));
  }
}
