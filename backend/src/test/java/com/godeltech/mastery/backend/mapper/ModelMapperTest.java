package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;

class ModelMapperTest {

  private final ModelMapper modelMapper = getMapper(ModelMapper.class);

  @Test
  void map() {
    final var model = new Model(1L, "Emgrand", new Brand());
    final var expected = new ModelDTO(1L, "Emgrand");

    final var actual = modelMapper.map(model);

    assertThat(actual, is(expected));
  }
}
