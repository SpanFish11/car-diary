package com.godeltech.mastery.backend.service.impl;

import static java.util.stream.Collectors.joining;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import com.godeltech.mastery.backend.domain.entity.Role;
import com.godeltech.mastery.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements UserDetailsService {

  private final ClientService clientService;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

    final var client = clientService.getClientByEmail(username);

    return new User(
        client.getEmail(),
        client.getPassword(),
        commaSeparatedStringToAuthorityList(
            client.getRoles().stream().map(Role::getRoleName).collect(joining(","))));
  }
}
