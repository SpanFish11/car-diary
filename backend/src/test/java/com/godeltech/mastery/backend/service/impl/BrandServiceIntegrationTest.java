package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "/tests/brands/initTestDB.sql")
class BrandServiceIntegrationTest {

  @Autowired private BrandService brandService;

  @Test
  void getAllBrands() {
    final List<BrandDTO> expected = List.of(new BrandDTO(1L, "Nissan"), new BrandDTO(2L, "Geely"));

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
}
