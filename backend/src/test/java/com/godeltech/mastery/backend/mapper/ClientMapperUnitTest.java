package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.request.ClientCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;

@ExtendWith(SpringExtension.class)
class ClientMapperUnitTest {

  private final ClientMapper clientMapper = getMapper(ClientMapper.class);

  @Test
  void testToDto() {
    final var client = new Client(2L, "John", "Snow", "snow324@mail.com", "dsfsdfs", Set.of());
    final var expected = new ClientDTO(2L, "John", "Snow", "snow324@mail.com");

    final var actual = clientMapper.toDto(client);

    assertThat(actual, is(expected));
  }

  @Test
  void fromRequest() {
    final var request = new ClientCreateRequest("John", "Snow", "snow324@mail.com");
    final var expected =
        Client.builder().firstName("John").lastName("Snow").email("snow324@mail.com").build();

    final var actual = clientMapper.fromRequest(request);

    assertThat(actual, is(expected));
  }
}
