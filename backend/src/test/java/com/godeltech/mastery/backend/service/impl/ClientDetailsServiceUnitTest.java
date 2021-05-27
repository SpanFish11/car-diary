package com.godeltech.mastery.backend.service.impl;

import static com.godeltech.mastery.backend.domain.entity.SystemRoles.ROLE_USER;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.entity.Role;
import com.godeltech.mastery.backend.service.ClientService;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ClientDetailsServiceUnitTest {

  @Mock ClientService clientService;

  @InjectMocks ClientDetailsServiceImpl detailsService;

  @Test
  void loadUserByUsername() {
    final var email = "frg45speed@mail.com";
    final var client =
        Client.builder()
            .email(email)
            .password("dsfds2342")
            .roles(Set.of(Role.builder().roleName(ROLE_USER.name()).build()))
            .build();
    final var expected =
        new User(
            client.getEmail(),
            client.getPassword(),
            commaSeparatedStringToAuthorityList(
                client.getRoles().stream().map(Role::getRoleName).collect(joining(","))));

    given(clientService.getClientByEmail(email)).willReturn(client);

    final var actual = detailsService.loadUserByUsername(email);
    assertThat(actual, is(expected));

    then(clientService).should(only()).getClientByEmail(email);
  }
}
