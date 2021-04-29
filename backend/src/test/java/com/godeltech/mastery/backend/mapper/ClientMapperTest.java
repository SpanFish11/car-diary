package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.ClientDTO;
import com.godeltech.mastery.backend.domain.entity.Client;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;

class ClientMapperTest {

  private final ClientMapper clientMapper = getMapper(ClientMapper.class);

  @Test
  void testToDto() {
    final var client = new Client(2L, "John", "Snow", "snow324@mail.com", "dsfsdfs", Set.of());
    final var expected = new ClientDTO(2L, "John", "Snow", "snow324@mail.com");

    final var actual = clientMapper.toDto(client);

    assertThat(actual, is(expected));
  }
}
