package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {ModelMapper.class})
public interface BrandMapper {

  @Mappings({
    @Mapping(target = "id", source = "id"),
    @Mapping(target = "name", source = "name"),
    @Mapping(target = "models", source = "models")
  })
  BrandDTO map(Brand brand);

  List<BrandDTO> map(List<Brand> brands);
}
