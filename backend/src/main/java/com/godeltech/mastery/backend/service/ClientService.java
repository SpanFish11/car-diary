package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;

import java.util.List;

public interface ClientService {

  Client getClientById(Long id);

  List<ClientDTO> getAllClients();

  Long createClient(ClientCreateRequest request);
}
