package com.godeltech.mastery.backend.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BrandMapperTest {

  private final BrandMapper brandMapper = getMapper(BrandMapper.class);

  @Test
  void testMap() {
    final var model = new Model(3L, "Curdden", new Brand());
    final var brandRequest = new Brand(1L, "Nissan", Set.of(model));

    final var expected = new BrandDTO(1L, "Nissan");

    final var actual = brandMapper.map(brandRequest);

    assertThat(actual, is(expected));
  }

  @Test
  void testMapList() {
    final var model = new Model(8L, "Card", new Brand());
    final var brandsRequest =
        List.of(new Brand(1L, "Nissan", Set.of(model)), new Brand(2L, "Geely", null));

    final var expected = List.of(new BrandDTO(1L, "Nissan"), new BrandDTO(2L, "Geely"));

    final var actual = brandMapper.map(brandsRequest);

    assertThat(actual, is(expected));
  }
}
