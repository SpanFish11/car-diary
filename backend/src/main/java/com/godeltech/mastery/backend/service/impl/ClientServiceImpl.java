package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.ClientDTO;
import com.godeltech.mastery.backend.domain.event.ClientCreateEvent;
import com.godeltech.mastery.backend.mapper.ClientMapper;
import com.godeltech.mastery.backend.repository.ClientRepository;
import com.godeltech.mastery.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;
  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;

  @Override
  public List<ClientDTO> getAllClients() {
    return clientMapper.toDtoList(clientRepository.findAll());
  }

  @Override
  public Long createClient(final ClientCreateRequest request) {
    checkEmailExist(request.getEmail());
    final var client = clientMapper.fromRequest(request);
    client.setPassword(randomAlphabetic(8));
    final var clientId = clientRepository.save(client).getId();
    simpleApplicationEventMulticaster.multicastEvent(new ClientCreateEvent(this, client));
    return clientId;
  }

  private void checkEmailExist(final String email) {
    final var bool = clientRepository.existsByEmailIgnoreCase(email);
    if (TRUE.equals(bool)) {
      throw new IllegalArgumentException(format("Email %s already exists", email));
    }
  }
}
