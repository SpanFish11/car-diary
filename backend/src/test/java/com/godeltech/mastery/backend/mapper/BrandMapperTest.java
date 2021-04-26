package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandMapperTest {

  @Mock private ModelMapper modelMapper;
  @InjectMocks private BrandMapper brandMapper = Mappers.getMapper(BrandMapper.class);

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void testMap() {
    final Model model = new Model(3L, "Curdden", new Brand());
    final Brand brandRequest = new Brand(1L, "Nissan", Set.of(model));
    final ModelDTO modelDTO = new ModelDTO(3L, "Curdden");
    final BrandDTO expected = new BrandDTO(1L, "Nissan", Set.of(modelDTO));

    given(modelMapper.map(model)).willReturn(modelDTO);

    final BrandDTO actual = brandMapper.map(brandRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
  }

  @Test
  void testMapList() {
    final Model model = new Model(8L, "Card", new Brand());
    final List<Brand> brandsRequest =
        List.of(new Brand(1L, "Nissan", Set.of(model)), new Brand(2L, "Geely", null));
    final ModelDTO modelDTO = new ModelDTO(8L, "Card");
    final List<BrandDTO> expected =
        List.of(new BrandDTO(1L, "Nissan", Set.of(modelDTO)), new BrandDTO(2L, "Geely", null));

    given(modelMapper.map(model)).willReturn(modelDTO);

    final List<BrandDTO> actual = brandMapper.map(brandsRequest);

    assertThat(actual, is(expected));

    then(modelMapper).should(only()).map(model);
  }
}
