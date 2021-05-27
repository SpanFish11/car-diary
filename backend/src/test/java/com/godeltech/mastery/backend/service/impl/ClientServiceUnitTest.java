package com.godeltech.mastery.backend.service.impl;

import static com.godeltech.mastery.backend.domain.entity.SystemRoles.ROLE_USER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.request.ResetPasswordRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.entity.Role;
import com.godeltech.mastery.backend.domain.event.ClientCreateEvent;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.ClientMapper;
import com.godeltech.mastery.backend.repository.ClientRepository;
import com.godeltech.mastery.backend.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ClientServiceUnitTest {

  @Mock SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;
  @Mock ClientRepository clientRepository;
  @Mock ClientMapper clientMapper;
  @Mock PasswordEncoder bCryptPasswordEncoder;
  @Mock RoleService roleService;

  @InjectMocks ClientServiceImpl clientService;

  @Test
  @WithMockUser(value = "someUser@app.com")
  void getClient() {
    final User auth = (User) getContext().getAuthentication().getPrincipal();

    final var expected =
        Client.builder()
            .id(34L)
            .firstName("John")
            .lastName("Snow")
            .email(auth.getUsername())
            .build();

    given(clientRepository.getByEmailIgnoreCase(auth.getUsername()))
        .willReturn(Optional.of(expected));

    final var actual = clientService.getClient(auth);
    assertThat(actual, is(expected));

    then(clientRepository).should(only()).getByEmailIgnoreCase(auth.getUsername());
  }

  @Test
  void getClientByCorrectId() {
    final var id = 1L;
    final var expected =
        Client.builder()
            .id(id)
            .firstName("John")
            .lastName("Snow")
            .email("snow654@exam.com")
            .build();

    given(clientRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = clientService.getClientById(id);
    assertThat(actual, is(expected));

    then(clientRepository).should(only()).findById(eq(id));
  }

  @Test
  void getClientByIncorrectId() {
    final var incorrectId = 345L;
    final var message = format("Could not find any client with the ID %d.", incorrectId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(clientRepository.findById(incorrectId)).willThrow(excepted);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(incorrectId));
    assertThat(actual.getMessage(), is(message));

    then(clientRepository).should(only()).findById(eq(incorrectId));
  }

  @Test
  void getClientByCorrectEmail() {
    final var email = "craze2frog@mail.com";
    final var expected =
        Client.builder().id(34L).firstName("John").lastName("Snow").email(email).build();

    given(clientRepository.getByEmailIgnoreCase(email)).willReturn(Optional.of(expected));

    final var actual = clientService.getClientByEmail(email);
    assertThat(actual, is(expected));

    then(clientRepository).should(only()).getByEmailIgnoreCase(email);
  }

  @Test
  void getClientByIncorrectEmail() {
    final var incorrectEmail = "sdfsfe3123@fsfsf.ew";
    final var message = format("Could not find any client with the email = %s.", incorrectEmail);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(clientRepository.getByEmailIgnoreCase(incorrectEmail)).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> clientService.getClientByEmail(incorrectEmail));
    assertThat(actual.getMessage(), is(message));

    then(clientRepository).should(only()).getByEmailIgnoreCase(eq(incorrectEmail));
  }

  @Test
  void getAllClients() {
    final List<Client> clients = List.of(Client.builder().id(1L).email("snow34@mail.com").build());
    final List<ClientDTO> expected = List.of(new ClientDTO(1L, "John", "Snow", "snow34@mail.com"));

    given(clientRepository.findAll()).willReturn(clients);
    given(clientMapper.toDtoList(clients)).willReturn(expected);

    final List<ClientDTO> actual = clientService.getAllClients();
    assertThat(actual, is(expected));

    then(clientRepository).should(only()).findAll();
    then(clientMapper).should(atLeastOnce()).toDtoList(refEq(clients));
  }

  @Test
  void createClient() {
    final var request =
        ClientCreateRequest.builder()
            .firstName("John")
            .lastName("Snow")
            .email("snow324@mail.com")
            .build();
    final var client =
        Client.builder().firstName("John").lastName("Snow").email("snow324@mail.com").build();

    final var expected = client.toBuilder().id(1L).password("JhdtgFkK").build();

    final var mockStatic = mockStatic(RandomStringUtils.class);

    given(clientRepository.existsByEmailIgnoreCase(client.getEmail())).willReturn(FALSE);
    given(clientMapper.fromRequest(request)).willReturn(client);
    mockStatic.when(() -> randomAlphanumeric(8)).thenReturn("JhdtgFkK");
    given(roleService.getRoleByRoleName(ROLE_USER.name()))
        .willReturn(Role.builder().roleName(ROLE_USER.name()).build());
    given(clientRepository.save(client)).willReturn(expected);
    willDoNothing()
        .given(simpleApplicationEventMulticaster)
        .multicastEvent(new ClientCreateEvent(this, client, null));

    final var actual = clientService.createClient(request);

    assertThat(actual, is(expected.getId()));
    assertThat(randomAlphanumeric(8), is(expected.getPassword()));

    then(clientRepository)
        .should(atLeastOnce())
        .existsByEmailIgnoreCase(argThat(client.getEmail()::equals));
    then(clientMapper).should(only()).fromRequest(argThat(request::equals));
    then(clientRepository).should(atLeastOnce()).save(argThat(client::equals));
    then(roleService).should(only()).getRoleByRoleName(eq(ROLE_USER.name()));
    then(simpleApplicationEventMulticaster)
        .should(only())
        .multicastEvent(any(ClientCreateEvent.class));
  }

  @Test
  void createClientIfEmailExist() {
    final var email = "snow34@mail.com";
    final var request = ClientCreateRequest.builder().email(email).build();
    final var message = format("Email %s already exists", email);

    given(clientRepository.existsByEmailIgnoreCase(email)).willReturn(TRUE);

    final var actual =
        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(request));
    assertThat(actual.getMessage(), is(message));

    then(clientRepository).should(only()).existsByEmailIgnoreCase(argThat(email::equals));
  }

  @Test
  @WithMockUser
  void changePassword() {
    final User auth = (User) getContext().getAuthentication().getPrincipal();
    final ResetPasswordRequest request = new ResetPasswordRequest();
    request.setOldPassword("sDrw324");
    final var client =
        Client.builder().password(request.getOldPassword()).email(auth.getUsername()).build();

    given(clientRepository.getByEmailIgnoreCase(auth.getUsername()))
        .willReturn(Optional.of(client));
    given(
            bCryptPasswordEncoder.matches(
                request.getOldPassword(), clientService.getClient(auth).getPassword()))
        .willReturn(true);

    clientService.changePassword(request, auth);

    then(clientRepository).should(times(2)).getByEmailIgnoreCase(eq(auth.getUsername()));
    then(bCryptPasswordEncoder).should(times(1)).matches(eq(request.getOldPassword()), anyString());
  }

  @Test
  @WithMockUser(username = "someUser@app.com", password = "sDrw324")
  void changePasswordIfPasswordNotMatch() {
    final User auth = (User) getContext().getAuthentication().getPrincipal();
    final ResetPasswordRequest request = new ResetPasswordRequest();
    request.setOldPassword("dfsdfe5343");
    final var client = Client.builder().password("sDrw324").email(auth.getUsername()).build();
    final String message = "Passwords do not match!";

    given(clientRepository.getByEmailIgnoreCase(auth.getUsername()))
        .willReturn(Optional.of(client));
    given(
            bCryptPasswordEncoder.matches(
                request.getOldPassword(), clientService.getClient(auth).getPassword()))
        .willReturn(false);

    final var actual =
        assertThrows(
            IllegalArgumentException.class, () -> clientService.changePassword(request, auth));
    assertThat(actual.getMessage(), is(message));

    then(clientRepository).should(times(2)).getByEmailIgnoreCase(eq(auth.getUsername()));
    then(bCryptPasswordEncoder)
        .should(only())
        .matches(request.getOldPassword(), clientService.getClient(auth).getPassword());
  }
}
