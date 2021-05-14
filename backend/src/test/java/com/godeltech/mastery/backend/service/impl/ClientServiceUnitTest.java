package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.event.ClientCreateEvent;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.ClientMapper;
import com.godeltech.mastery.backend.repository.ClientRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.only;

@ExtendWith(SpringExtension.class)
class ClientServiceUnitTest {

  @Mock private ClientMapper clientMapper;
  @Mock private ClientRepository clientRepository;
  @Mock private SimpleApplicationEventMulticaster simpleApplicationEventMulticaster;

  @InjectMocks private ClientServiceImpl clientService;

  @Test
  void getClientById_given_correct_id() {
    final var id = 1L;
    final var expected =
        Client.builder().firstName("John").lastName("Snow").email("snow324@mail.com").build();

    given(clientRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = clientService.getClientById(id);
    assertThat(actual, is(expected));

    then(clientRepository).should(only()).findById(eq(id));
  }

  @Test
  void getClientById_given_correct_id_by_notFound() {
    final var id = 50L;
    final var exception = new EntityNotFoundException("client", id);
    final var message = exception.getMessage();

    given(clientRepository.findById(id)).willReturn(empty());

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById(id));
    assertThat(actual.getMessage(), is(message));

    then(clientRepository).should(only()).findById(eq(id));
  }

  @Test
  void getAllClients() {
    final var clients =
        List.of(
            Client.builder()
                .id(1L)
                .firstName("John")
                .lastName("Snow")
                .email("snow324@mail.com")
                .build(),
            Client.builder()
                .id(2L)
                .firstName("Abigail")
                .lastName("Watkins")
                .email("mewok99092@gridmire.com")
                .build());
    final var expected =
        List.of(
            new ClientDTO(1L, "John", "Snow", "snow324@mail.com"),
            new ClientDTO(2L, "Abigail", "Watkins", "mewok99092@gridmire.com"));

    given(clientRepository.findAll()).willReturn(clients);
    given(clientMapper.toDtoList(clients)).willReturn(expected);

    final var actual = clientService.getAllClients();
    assertThat(actual, is(expected));

    then(clientRepository).should(only()).findAll();
    then(clientMapper).should(only()).toDtoList(argThat(clients::equals));
  }

  @Test
  void createClient_given_uniqueEmail() {
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
    then(simpleApplicationEventMulticaster)
        .should(only())
        .multicastEvent(any(ClientCreateEvent.class));
  }

  @Test
  void createClient_given_not_uniqueEmail() {
    final var email = "snow324@mail.com";
    final var request = ClientCreateRequest.builder().email(email).build();
    final var message = format("Email %s already exists", email);

    given(clientRepository.existsByEmailIgnoreCase(email)).willReturn(TRUE);

    final var actual =
        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(request));
    assertThat(actual.getMessage(), is(message));

    then(clientRepository).should(only()).existsByEmailIgnoreCase(argThat(email::equals));
  }
}
