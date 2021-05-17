package com.godeltech.mastery.backend.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.godeltech.mastery.backend.domain.dto.responce.CarDTO;
import com.godeltech.mastery.backend.rest.BrandController;
import com.godeltech.mastery.backend.rest.GuaranteeController;
import com.godeltech.mastery.backend.rest.OperationRecordController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CarModelAssembler
    implements RepresentationModelAssembler<CarDTO, EntityModel<CarDTO>> {

  @Override
  public EntityModel<CarDTO> toModel(CarDTO entity) {
    return EntityModel.of(
        entity,
        linkTo(methodOn(BrandController.class).getBrandById(entity.getBrand().getId()))
            .withRel("brand"),
        linkTo(methodOn(GuaranteeController.class).getGuarantee(entity.getId()))
            .withRel("guarantee"),
        linkTo(methodOn(OperationRecordController.class).getCarOperations(entity.getId()))
            .withRel("service operations"));
  }
}
