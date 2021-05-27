package com.godeltech.mastery.backend.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;

import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import java.util.List;
import org.junit.jupiter.api.Test;

class ModelMapperTest {

  private final ModelMapper modelMapper = getMapper(ModelMapper.class);

  @Test
  void testMap() {
    final var model = new Model(1L, "Emgrand", new Brand());
    final var expected = new ModelDTO(1L, "Emgrand");

    final var actual = modelMapper.map(model);

    assertThat(actual, is(expected));
  }

  @Test
  void testMapList() {
    final var modelList =
        List.of(new Model(1L, "Emgrand", new Brand()), new Model(2L, "X-Trail", new Brand()));
    final var expected = List.of(new ModelDTO(1L, "Emgrand"), new ModelDTO(2L, "X-Trail"));

    final var actual = modelMapper.map(modelList);

    assertThat(actual, is(expected));
  }
}
