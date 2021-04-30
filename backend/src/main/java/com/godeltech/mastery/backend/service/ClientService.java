package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.ClientDTO;

import java.util.List;

public interface ClientService {

  List<ClientDTO> getAllClients();

  Long createClient(ClientCreateRequest request);
}
