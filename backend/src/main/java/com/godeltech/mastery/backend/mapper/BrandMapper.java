package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {ModelMapper.class})
public interface BrandMapper {

  BrandDTO map(Brand brand);

  List<BrandDTO> map(List<Brand> brands);
}
