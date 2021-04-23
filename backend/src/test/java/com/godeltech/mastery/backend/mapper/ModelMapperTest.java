package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ModelMapperTest {

  private ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

  @Test
  void map() {
    final Model model = new Model(1L, "Emgrand", new Brand());
    final ModelDTO expected = new ModelDTO(1L, "Emgrand");

    final ModelDTO actual = modelMapper.map(model);

    assertThat(actual, is(expected));
  }
}
