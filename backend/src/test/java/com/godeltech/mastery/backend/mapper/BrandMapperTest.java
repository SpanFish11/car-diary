package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandMapperTest {

  @Mock private ModelMapper modelMapper;

  @InjectMocks private final BrandMapper brandMapper = getMapper(BrandMapper.class);

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void testMap() {
    final var model = new Model(3L, "Curdden", new Brand());
    final var brandRequest = new Brand(1L, "Nissan", Set.of(model));
    final var modelDTO = new ModelDTO(3L, "Curdden");
    final var expected = new BrandDTO(1L, "Nissan", Set.of(modelDTO));

    given(modelMapper.map(model)).willReturn(modelDTO);

    final var actual = brandMapper.map(brandRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
  }

  @Test
  void testMapList() {
    final var model = new Model(8L, "Card", new Brand());
    final var brandsRequest =
        List.of(new Brand(1L, "Nissan", Set.of(model)), new Brand(2L, "Geely", null));
    final var modelDTO = new ModelDTO(8L, "Card");
    final var expected =
        List.of(new BrandDTO(1L, "Nissan", Set.of(modelDTO)), new BrandDTO(2L, "Geely", null));

    given(modelMapper.map(model)).willReturn(modelDTO);

    final var actual = brandMapper.map(brandsRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
  }
}
