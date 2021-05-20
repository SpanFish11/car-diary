package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.GuaranteeDTO;
import com.godeltech.mastery.backend.domain.entity.Guarantee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface GuaranteeMapper {

  @Mapping(target = "start", source = "start")
  @Mapping(target = "extended", source = "extended")
  Guarantee toEntity(GuaranteeCreateRequest request);

  @Mapping(target = "end", source = "end")
  @Mapping(target = "start", source = "start")
  @Mapping(target = "mileage", source = "mileage")
  @Mapping(target = "extended", source = "extended")
  GuaranteeDTO toDTO(Guarantee guarantee);
}
