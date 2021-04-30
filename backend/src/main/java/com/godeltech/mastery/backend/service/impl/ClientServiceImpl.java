package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.ClientDTO;
import com.godeltech.mastery.backend.mapper.ClientMapper;
import com.godeltech.mastery.backend.repository.ClientRepository;
import com.godeltech.mastery.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;

  @Override
  public List<ClientDTO> getAllClients() {
    return clientMapper.toDtoList(clientRepository.findAll());
  }

  @Override
  public Long createClient(final ClientCreateRequest request) {
    final var client = clientMapper.fromRequest(request);
    return clientRepository.save(client).getId();
  }
}
