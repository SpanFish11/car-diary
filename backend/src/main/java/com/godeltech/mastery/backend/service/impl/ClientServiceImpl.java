package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.event.ClientCreateEvent;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.ClientMapper;
import com.godeltech.mastery.backend.repository.ClientRepository;
import com.godeltech.mastery.backend.service.ClientService;
import com.godeltech.mastery.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.godeltech.mastery.backend.domain.entity.SystemRoles.ROLE_USER;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

  private final SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;
  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final RoleService roleService;

  @Override
  public Client getClient(final Authentication principal) {
    final var clientEmail = principal.getName();
    if (isEmpty(clientEmail)) {
      throw new AccessDeniedException("Invalid access");
    }
    return getClientByEmail(clientEmail);
  }

  @Override
  public Client getClientById(final Long id) {
    return clientRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("client", id));
  }

  @Override
  public Client getClientByEmail(final String email) {
    return clientRepository
        .getByEmailIgnoreCase(email)
        .orElseThrow(() -> new EntityNotFoundException("client", email));
  }

  @Override
  public List<ClientDTO> getAllClients() {
    return clientMapper.toDtoList(clientRepository.findAll());
  }

  @Override
  public Long createClient(final ClientCreateRequest request) {
    checkEmailExist(request.getEmail());
    final var client = clientMapper.fromRequest(request);
    final var password = randomAlphabetic(8);
    client.setPassword(bCryptPasswordEncoder.encode(password));
    final var role = roleService.getRoleByRoleName(ROLE_USER.name());
    client.setRoles(new HashSet<>(Set.of(role)));
    final var clientId = clientRepository.save(client).getId();
    simpleApplicationEventMulticaster.multicastEvent(new ClientCreateEvent(this, client, password));
    return clientId;
  }

  private void checkEmailExist(final String email) {
    final var bool = clientRepository.existsByEmailIgnoreCase(email);
    if (TRUE.equals(bool)) {
      throw new IllegalArgumentException(format("Email %s already exists", email));
    }
  }
}
