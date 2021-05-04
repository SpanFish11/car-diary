package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(SpringExtension.class)
class ModelServiceUnitTest {

  @Mock private ModelRepository modelRepository;

  @InjectMocks private ModelServiceImpl modelService;

  @Test
  void getModelById_given_correctId() {
    final var id = 1L;
    final var expected = new Model(1L, "Emgrand", new Brand());

    given(modelRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = modelService.getModelById(id);
    assertThat(actual, is(expected));

    then(modelRepository).should(only()).findById(eq(id));
  }

  @Test
  void getModelById_given_id_by_notFound() {
    final var id = 500L;
    final var exception = new EntityNotFoundException("model", id);
    final var message = exception.getMessage();

    given(modelRepository.findById(id)).willReturn(empty());

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> modelService.getModelById(id));
    assertThat(actual.getMessage(), is(message));

    then(modelRepository).should(only()).findById(eq(id));
  }
}
