package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

  ModelDTO map(Model model);

  List<ModelDTO> map(List<Model> models);
}
