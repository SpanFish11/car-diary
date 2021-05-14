package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.responce.BrandDTO;
import com.godeltech.mastery.backend.domain.dto.responce.ModelDTO;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.ResponseEntity.ok;

@ExtendWith(SpringExtension.class)
class BrandControllerUnitTest {

  @Mock BrandService brandService;

  @InjectMocks BrandController brandController;

  @Test
  void getAllBrands() {
    final var brands = List.of(new BrandDTO(1L, "Nissan"), new BrandDTO(2L, "Geely"));
    final ResponseEntity<List<BrandDTO>> expected = ok(brands);

    given(brandService.getAllBrands()).willReturn(brands);

    final ResponseEntity<List<BrandDTO>> actual = brandController.getAllBrands();
    assertThat(actual, is(expected));

    then(brandService).should(only()).getAllBrands();
  }

  @Test
  void getAllModelById_givenId_shouldReturnModels() {
    final Long brandId = 1L;
    final var models = List.of(new ModelDTO(3L, "Tuggella"), new ModelDTO(9L, "Atlas"));
    final ResponseEntity<List<ModelDTO>> expected = ok(models);

    given(brandService.getModelsByBrandId(brandId)).willReturn(models);

    final ResponseEntity<List<ModelDTO>> actual = brandController.getAllModelById(brandId);
    assertThat(actual, is(expected));

    then(brandService).should(only()).getModelsByBrandId(argThat(brandId::equals));
  }

  @Test
  void getAllModelById_givenId_shouldTrowException_NotFoundBrand() {
    final Long brandId = 5555L;
    final var exception = new EntityNotFoundException("brand", brandId);
    final var message = exception.getMessage();

    given(brandService.getModelsByBrandId(brandId)).willThrow(exception);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> brandController.getAllModelById(brandId));
    assertThat(actual.getMessage(), is(message));

    then(brandService).should(only()).getModelsByBrandId(argThat(brandId::equals));
  }
}
