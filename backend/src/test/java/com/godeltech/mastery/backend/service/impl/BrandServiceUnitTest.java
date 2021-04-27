package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
import com.godeltech.mastery.backend.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandServiceUnitTest {

  @Mock BrandRepository brandRepository;
  @Mock BrandMapper brandMapper;

  @InjectMocks BrandServiceImpl brandService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void getAllBrands() {
    final List<Brand> brands =
        List.of(new Brand(1L, "Nissan", Set.of(new Model(1L, "Murano", new Brand()))));
    final List<BrandDTO> expected =
        List.of(new BrandDTO(1L, "Nissan", Set.of(new ModelDTO(1L, "Murano"))));

    given(brandRepository.findAll()).willReturn(brands);
    given(brandMapper.map(brands)).willReturn(expected);

    final List<BrandDTO> actual = brandService.getAllBrands();
    assertThat(actual, containsInAnyOrder(expected.toArray()));

    then(brandRepository).should(only()).findAll();
  }

  @Test
  void getModelsByCorrectBrandId() {
    final Set<Model> models =
        Set.of(new Model(1L, "Coolray", new Brand()), new Model(2L, "Emgrand", new Brand()));
    final Set<ModelDTO> modelsDTO =
        Set.of(new ModelDTO(1L, "Coolray"), new ModelDTO(2L, "Emgrand"));
    final Long id = 2L;
    final Brand expected = new Brand(id, "Nissan", models);
    final BrandDTO brandDTO = new BrandDTO(id, "Nissan", modelsDTO);

    given(brandRepository.findById(id)).willReturn(Optional.of(expected));
    given(brandMapper.map(expected)).willReturn(brandDTO);

    final List<ModelDTO> actual = brandService.getModelsByBrandId(2L);
    assertThat(actual, containsInAnyOrder(modelsDTO.toArray()));

    then(brandRepository).should(only()).findById(id);
  }

  @Test
  void getModelsByIncorrectBrandId() {
    final Long id = 233L;
    final String message = format("Brand or brand models are not found with brand id %d", id);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(brandRepository.findById(id)).willThrow(excepted);

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> brandService.getModelsByBrandId(id));
    assertThat(actual.getMessage(), is(message));

    then(brandRepository).should(only()).findById(id);
  }
}
