package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
import com.godeltech.mastery.backend.mapper.ModelMapper;
import com.godeltech.mastery.backend.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class BrandServiceUnitTest {

  @Mock BrandRepository brandRepository;
  @Mock BrandMapper brandMapper;
  @Mock ModelMapper modelMapper;

  @InjectMocks BrandServiceImpl brandService;

  @Test
  void getAllBrands() {
    final List<Brand> brands =
        List.of(new Brand(1L, "Nissan", Set.of(new Model(1L, "Murano", new Brand()))));
    final List<BrandDTO> expected = List.of(new BrandDTO(1L, "Nissan"));

    given(brandRepository.findAll()).willReturn(brands);
    given(brandMapper.map(brands)).willReturn(expected);

    final List<BrandDTO> actual = brandService.getAllBrands();
    assertThat(actual, is(expected));

    then(brandRepository).should(only()).findAll();
    then(brandMapper).should(atLeastOnce()).map(refEq(brands));
  }

  @Test
  void getBrandById_given_CorrectId() {
    final var id = 1L;
    final var expected = new Brand(id, "Nissan", Set.of(new Model(1L, "Murano", new Brand())));

    given(brandRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = brandService.getById(id);
    assertThat(actual, is(expected));

    then(brandRepository).should(only()).findById(eq(id));
  }

  @Test
  void getBrandById_given_IncorrectId() {
    final var incorrectId = 999L;
    final var message = format("Brand or brand models are not found with brand id %d", incorrectId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(brandRepository.findById(incorrectId)).willThrow(excepted);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> brandService.getById(incorrectId));
    assertThat(actual.getMessage(), is(message));

    then(brandRepository).should(only()).findById(eq(incorrectId));
  }

  @Test
  void getModelsByBrandId_given_CorrectId() {
    final Long id = 2L;
    final Brand brand =
        new Brand(
            id,
            "Nissan",
            Set.of(new Model(1L, "Coolray", new Brand()), new Model(2L, "Emgrand", new Brand())));
    final List<Model> models = brand.getModels().stream().toList();
    final List<ModelDTO> expected =
        List.of(new ModelDTO(1L, "Coolray"), new ModelDTO(2L, "Emgrand"));

    given(brandRepository.findById(id)).willReturn(Optional.of(brand));
    given(modelMapper.map(models.get(0))).willReturn(expected.get(0));
    given(modelMapper.map(models.get(1))).willReturn(expected.get(1));

    final var actual = brandService.getModelsByBrandId(2L);
    assertThat(actual, is(expected));

    then(brandRepository).should(only()).findById(eq(id));
    then(modelMapper).should(times(2)).map(any(Model.class));
  }

  @Test
  void getModelsByBrandId_given_IncorrectId() {
    final var incorrectId = 99L;
    final var message = format("Brand or brand models are not found with brand id %d", incorrectId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(brandRepository.findById(incorrectId)).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> brandService.getModelsByBrandId(incorrectId));
    assertThat(actual.getMessage(), is(message));

    then(brandRepository).should(only()).findById(eq(incorrectId));
  }
}
