package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
import com.godeltech.mastery.backend.mapper.ModelMapper;
import com.godeltech.mastery.backend.repository.BrandRepository;
import com.godeltech.mastery.backend.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "/tests/brands/initTestDB.sql")
class BrandServiceIntegrationTest {

  @Autowired private BrandMapper brandMapper;
  @Autowired private ModelMapper modelMapper;
  @Autowired private BrandRepository brandRepository;

  private BrandService brandService;

  @BeforeEach
  void setUp() {
    brandService = new BrandServiceImpl(brandRepository, brandMapper, modelMapper);
  }

  @Test
  void getAllBrands() {
    final List<BrandDTO> expected =
        List.of(
            new BrandDTO(1L, "Nissan"),
            new BrandDTO(
                2L, "Geely"));

    final List<BrandDTO> actual = brandService.getAllBrands();
    assertThat(actual, is(expected));
  }

  @Test
  void getModelsByBrandCorrectId() {
    final List<ModelDTO> expected = List.of(new ModelDTO(2L, "Terrano"));

    final List<ModelDTO> actual = brandService.getModelsByBrandId(1L);
    assertThat(actual, is(expected));
  }

  @Test
  void getModelsByBrandIncorrectId() {
    final Long id = 27L;
    final var message = format("Could not find any brand with the ID %d.", id);

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> brandService.getModelsByBrandId(id));

    assertThat(actual.getMessage(), is(message));
  }

  @Test
    void getBrandByCorrectId() {
      final Brand expected = new Brand(2L, "Geely", Set.of(new Model()));

      final Brand actual = brandService.getById(2L);

      assertThat(actual.getId(), is(expected.getId()));
      assertThat(actual.getName(), is(expected.getName()));
  }

    @Test
    void getBrandsByIncorrectId() {
      final Long id = 56L;
      final var message = format("Could not find any brand with the ID %d.", id);

      final EntityNotFoundException actual = assertThrows(EntityNotFoundException.class, () -> brandService.getById(id));

      assertThat(actual.getMessage(), is(message));
    }
}
