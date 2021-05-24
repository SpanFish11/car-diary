package com.godeltech.mastery.backend.service.impl;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.EquipmentMapper;
import com.godeltech.mastery.backend.repository.EquipmentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EquipmentServiceUnitTest {

  @Mock EquipmentRepository equipmentRepository;
  @Mock EquipmentMapper equipmentMapper;

  @InjectMocks EquipmentServiceImpl equipmentService;

  @Test
  void getEquipmentByCorrectId() {
    final var id = 1L;
    final var expected =
        new Equipment(id, "Basic configuration", "Petrol", "Manual Transmission", 2.0, 235);

    given(equipmentRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = equipmentService.getEquipmentById(id);
    assertThat(actual, is(expected));

    then(equipmentRepository).should(only()).findById(eq(id));
  }

  @Test
  void getEquipmentByIncorrectId() {
    final var incorrectId = 999L;
    final var message = format("Equipment is not found with id = %d", incorrectId);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(equipmentRepository.findById(incorrectId)).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class, () -> equipmentService.getEquipmentById(incorrectId));
    assertThat(actual.getMessage(), is(message));

    then(equipmentRepository).should(only()).findById(eq(incorrectId));
  }

  @Test
  void getEquipments() {
    final List<Equipment> equipments =
        List.of(
            new Equipment(1L, "Basic configuration", "Petrol", "Manual Transmission", 2.0, 235));
    final List<EquipmentDTO> expected =
        List.of(
            new EquipmentDTO(1L, "Basic configuration", "Petrol", "Manual Transmission", 2.0, 235));

    given(equipmentRepository.findAll()).willReturn(equipments);
    given(equipmentMapper.toDtos(equipments)).willReturn(expected);

    final List<EquipmentDTO> actual = equipmentService.getEquipments();
    assertThat(actual, is(expected));

    then(equipmentRepository).should(only()).findAll();
    then(equipmentMapper).should(atLeastOnce()).toDtos(refEq(equipments));
  }
}
