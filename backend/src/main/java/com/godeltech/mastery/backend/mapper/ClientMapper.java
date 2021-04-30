package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "firstName", source = "firstName")
  @Mapping(target = "lastName", source = "lastName")
  @Mapping(target = "email", source = "email")
  ClientDTO toDto(Client client);

  Client fromRequest(ClientCreateRequest request);

  List<ClientDTO> toDtoList(List<Client> clients);
}
