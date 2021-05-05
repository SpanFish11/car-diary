package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.ChangePartCreateRequest;
import com.godeltech.mastery.backend.domain.entity.ChangablePart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChangablePartMapper {

  @Mapping(target = "replaced", source = "replaced")
  @Mapping(target = "model", source = "model")
  @Mapping(target = "price", source = "price")
  ChangablePart toEntity(ChangePartCreateRequest request);

  List<ChangablePart> toEntityList(List<ChangePartCreateRequest> requests);
}
