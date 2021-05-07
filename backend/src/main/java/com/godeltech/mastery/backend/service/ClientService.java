package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

  Client getClient(Authentication principal);

  Client getClientById(Long id);

  Client getClientByEmail(String email);

  List<ClientDTO> getAllClients();

  Long createClient(ClientCreateRequest request);
}
