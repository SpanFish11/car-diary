package com.godeltech.mastery.backend.assembler;

import static org.mapstruct.factory.Mappers.getMapper;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.mapper.BrandMapper;
import com.godeltech.mastery.backend.rest.BrandController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BrandModelAssembler
    implements RepresentationModelAssembler<Brand, EntityModel<BrandDTO>> {

  BrandMapper brandMapper = getMapper(BrandMapper.class);

  @Override
  public EntityModel<BrandDTO> toModel(Brand entity) {
    BrandDTO brand = brandMapper.map(entity);
    return EntityModel.of(
        brand,
        linkTo(methodOn(BrandController.class).getBrandById(brand.getId())).withSelfRel(),
        linkTo(methodOn(BrandController.class).getAllBrands()).withRel("brands"));
  }

  public EntityModel<BrandDTO> dtoToModel(BrandDTO entity) {
    return EntityModel.of(
        entity, linkTo(methodOn(BrandController.class).getBrandById(entity.getId())).withSelfRel());
  }

  public EntityModel<ModelDTO> modelDTOToModel(ModelDTO model, Long id) {
    return EntityModel.of(
        model, linkTo(methodOn(BrandController.class).getAllModelById(id)).withRel("models"));
  }
}
