package com.godeltech.mastery.backend.service.impl;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.repository.ModelRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ModelServiceUnitTest {

  @Mock ModelRepository modelRepository;
  @InjectMocks ModelServiceImpl modelService;

  @Test
  void getModelByCorrectId() {
    final var id = 1L;
    final var expected = new Model(id, "Terrano", new Brand());

    given(modelRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = modelService.getModelById(id);
    assertThat(actual, is(expected));

    then(modelRepository).should(only()).findById(eq(id));
  }

  @Test
  void getModelByIncorrectId() {
    final var incorrectId = 999L;
    final var message = format("Model is not found with model id = %d", incorrectId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(modelRepository.findById(incorrectId)).willThrow(excepted);

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> modelService.getModelById(incorrectId));
    assertThat(actual.getMessage(), is(message));

    then(modelRepository).should(only()).findById(eq(incorrectId));
  }
}
