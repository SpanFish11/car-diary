package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.ModelDTO;
import com.godeltech.mastery.backend.service.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.OK;

class BrandControllerUnitTest {

  @Mock BrandService brandService;

  @InjectMocks BrandController brandController;

  @BeforeEach
  void setUp() {
    openMocks(this);
  }

  @Test
  void getAllBrands() {
    final List<BrandDTO> excepted =
        List.of(
            new BrandDTO(1L, "Nissan", Set.of(new ModelDTO(4L, "Card"))),
            new BrandDTO(2L, "Geely", Set.of(new ModelDTO(8L, "Tugella"))));

    given(brandService.getAllBrands()).willReturn(excepted);

    final ResponseEntity<List<BrandDTO>> actual = brandController.getAllBrands();
    assertThatResponseEntity(List.class, excepted, actual, OK);

    then(brandService).should(only()).getAllBrands();
  }

  @Test
  void getAllModelById() {
    final var brandId = 1L;
    final List<ModelDTO> excepted =
        List.of(new ModelDTO(3L, "Tuggella"), new ModelDTO(9L, "Atlas"));

    given(brandService.getModelsByBrandId(brandId)).willReturn(excepted);

    final ResponseEntity<List<ModelDTO>> actual = brandController.getAllModelById(brandId);
    assertThatResponseEntity(List.class, excepted, actual, OK);

    then(brandService).should(only()).getModelsByBrandId(brandId);
  }

  private <T> void assertThatResponseEntity(
      final Class<?> clazz,
      final List<T> excepted,
      final ResponseEntity<List<T>> actual,
      final HttpStatus status) {
    assertThat(actual.getBody(), isA(clazz));
    assertThat(actual.getBody(), is(excepted));
    assertThat(actual.getStatusCode(), is(status));
  }
}
