package com.godeltech.mastery.backend.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.godeltech.mastery.backend.domain.dto.request.ChangePartCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ChangeablePartDTO;
import com.godeltech.mastery.backend.domain.entity.ChangeablePart;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ChangeablePartMapper {

  @Mapping(target = "replaced", source = "replaced")
  @Mapping(target = "model", source = "model")
  @Mapping(target = "price", source = "price")
  ChangeablePart toEntity(ChangePartCreateRequest request);

  List<ChangeablePart> toEntityList(List<ChangePartCreateRequest> requests);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "replaced", source = "replaced")
  @Mapping(target = "model", source = "model")
  @Mapping(target = "price", source = "price")
  ChangeablePartDTO toDTO(ChangeablePart changeablePart);

  List<ChangeablePartDTO> toDTOList(List<ChangeablePart> changeableParts);
}
