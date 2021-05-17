package com.godeltech.mastery.backend.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;
import com.godeltech.mastery.backend.rest.MaintenanceController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceModelAssembler implements
    RepresentationModelAssembler<MaintenanceDTO, EntityModel<MaintenanceDTO>> {

  @Override
  public EntityModel<MaintenanceDTO> toModel(MaintenanceDTO entity) {
    return EntityModel.of(
        entity, linkTo(methodOn(MaintenanceController.class).getAllMaintenances()).withRel("maintenances"));
  }
}
