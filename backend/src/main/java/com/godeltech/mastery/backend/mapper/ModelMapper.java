package com.godeltech.mastery.backend.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Model;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ModelMapper {

  ModelDTO map(Model model);

  List<ModelDTO> map(List<Model> models);
}
