package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  ModelDTO map(Model model);

  List<ModelDTO> map(List<Model> models);
}
