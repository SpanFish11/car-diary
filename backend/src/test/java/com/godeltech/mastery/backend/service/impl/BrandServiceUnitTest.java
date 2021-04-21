package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandServiceUnitTest {

  @Mock BrandRepository brandRepository;

  @InjectMocks BrandServiceImpl brandService;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void getAllBrands() {
    final List<Brand> expected = List.of(new Brand(1L, "Nissan", Set.of(new Model(1L, "Murano", new Brand()))));

    given(brandRepository.findAll()).willReturn(expected);
    final List<Brand> actual = brandService.getAllBrands();

    assertThat(actual, hasSize(1));
    assertThat(actual, containsInAnyOrder(expected.toArray()));

    then(brandRepository).should(only()).findAll();
  }

  @Test
  void getModelsByCorrectBrandId() {
    final Set<Model> models = Set.of(new Model(1L, "Coolray", new Brand()), new Model(2L, "Emgrand", new Brand()));
    final Long id = 2L;
    final Brand expected = new Brand(id, "Nissan", models);

    given(brandRepository.findById(id)).willReturn(Optional.of(expected));
    final List<Model> actual = brandService.getModelsByBrandId(2L);

    assertThat(actual, hasSize(2));
    assertThat(actual, containsInAnyOrder(models.toArray()));

    then(brandRepository).should(only()).findById(id);
  }

  @Test
  void getModelsByIncorrectBrandId() {
    final Long id = 233L;
    final String message = format("Brand or brand models are not found with brand id %d", id);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(brandRepository.findById(id)).willThrow(excepted);

    EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> brandService.getModelsByBrandId(id));
    assertThat(actual.getMessage(), is(message));

    then(brandRepository).should(only()).findById(id);
  }
}
