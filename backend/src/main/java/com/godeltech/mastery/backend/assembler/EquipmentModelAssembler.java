package com.godeltech.mastery.backend.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.rest.EquipmentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EquipmentModelAssembler
    implements RepresentationModelAssembler<EquipmentDTO, EntityModel<EquipmentDTO>> {

  @Override
  public EntityModel<EquipmentDTO> toModel(EquipmentDTO entity) {
    return EntityModel.of(
        entity, linkTo(methodOn(EquipmentController.class).getAllEqu()).withRel("equipments"));
  }
}
