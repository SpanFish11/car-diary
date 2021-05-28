package com.godeltech.mastery.backend.mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface ClientMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "firstName", source = "firstName")
  @Mapping(target = "lastName", source = "lastName")
  @Mapping(target = "email", source = "email")
  ClientDTO toDto(Client client);

  Client fromRequest(ClientCreateRequest request);

  List<ClientDTO> toDtoList(List<Client> clients);
}
