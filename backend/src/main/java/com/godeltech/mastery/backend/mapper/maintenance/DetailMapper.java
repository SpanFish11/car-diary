package com.godeltech.mastery.backend.mapper.maintenance;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.DetailDTO;
import com.godeltech.mastery.backend.domain.entity.maintenance.Detail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetailMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  DetailDTO toDTO(Detail detail);

  List<DetailDTO> toDTOList(List<Detail> details);
}
