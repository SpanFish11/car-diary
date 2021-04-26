package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.BrandMapper;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "/tests/initTestDB.sql")
class BrandServiceIntegrationTest {

  @Autowired private BrandMapper brandMapper;
  @Autowired private BrandRepository brandRepository;

  private BrandService brandService;

  @BeforeEach
  void setUp() {
    brandService = new BrandServiceImpl(brandRepository, brandMapper);
  }

  @Test
  void getAllBrands() {
    final List<BrandDTO> expected =
        List.of(
            new BrandDTO(1L, "Nissan", Set.of(new ModelDTO(2L, "Terrano"))),
            new BrandDTO(
                2L, "Geely", Set.of(new ModelDTO(1L, "Coolray"), new ModelDTO(3L, "Emgrand"))));

    final List<BrandDTO> actual = brandService.getAllBrands();

    assertThat(actual, hasSize(2));
    assertThat(actual, is(expected));
  }

  @Test
  void getModelsByBrandCorrectId() {
    final List<ModelDTO> expected = List.of(new ModelDTO(2L, "Terrano"));

    final List<ModelDTO> actual = brandService.getModelsByBrandId(1L);

    assertThat(actual, hasSize(1));
    assertThat(actual, is(expected));
  }

  @Test
  void getModelsByBrandIncorrectId() {
    final Long id = 27L;
    final String message = format("Could not find any Brand with the ID %d.", id);

    final EntityNotFoundException actual =
        assertThrows(EntityNotFoundException.class, () -> brandService.getModelsByBrandId(id));

    assertThat(actual.getMessage(), is(message));
  }
}
