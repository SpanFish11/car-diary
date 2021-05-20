package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.ServiceWorkCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ServiceWorkDTO;
import com.godeltech.mastery.backend.domain.entity.ServiceWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ServiceWorkMapper {

  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "guarantee", source = "guarantee")
  ServiceWork toEntity(ServiceWorkCreateRequest request);

  List<ServiceWork> toListService(List<ServiceWorkCreateRequest> requests);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "guarantee", source = "guarantee")
  ServiceWorkDTO toDTO(ServiceWork serviceWork);

  List<ServiceWorkDTO> toDTOList(List<ServiceWork> serviceWorks);
}
