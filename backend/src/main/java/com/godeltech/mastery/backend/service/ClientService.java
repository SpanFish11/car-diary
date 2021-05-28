package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ResetPasswordRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import java.util.List;
import org.springframework.security.core.userdetails.User;

public interface ClientService {

  Client getClient(User principal);

  Client getClientById(Long id);

  Client getClientByEmail(String email);

  List<ClientDTO> getAllClients();

  Long createClient(ClientCreateRequest request);

  void changePassword(ResetPasswordRequest request, User principal);
}
