package com.godeltech.mastery.backend.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.assembler.EquipmentModelAssembler;
import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ExceptionResponseDTO;
import com.godeltech.mastery.backend.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Equipment Controller", description = "Operations about equipment")
@RestController
@RequestMapping("/api/v1/equipments")
@RequiredArgsConstructor
public class EquipmentController {

  private final EquipmentService equipmentService;
  private final EquipmentModelAssembler equipmentModelAssembler;

  @Operation(
      summary = "Get all equipments",
      description = "Endpoint for getting all equipments",
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
  public ResponseEntity<CollectionModel<EntityModel<EquipmentDTO>>> getAllEqu() {
    final List<EntityModel<EquipmentDTO>> equipments =
        equipmentService.getEquipments().stream()
            .map(equipmentModelAssembler::toModel)
            .collect(Collectors.toList());
    return ok(
        CollectionModel.of(
            equipments, linkTo(methodOn(EquipmentController.class).getAllEqu()).withSelfRel()));
  }
}
