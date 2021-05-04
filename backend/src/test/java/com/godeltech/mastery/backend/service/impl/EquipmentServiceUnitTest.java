package com.godeltech.mastery.backend.service.impl;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.EquipmentMapper;
import com.godeltech.mastery.backend.repository.EquipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(SpringExtension.class)
class EquipmentServiceUnitTest {

  @Mock private EquipmentMapper equipmentMapper;
  @Mock private EquipmentRepository equipmentRepository;

  @InjectMocks private EquipmentServiceImpl equipmentService;

  @Test
  void getEquipmentById_given_correct_id_and_equipment_found() {
    final var id = 1L;
    final var expected = new Equipment(1L, "Luxury", "Petrol", "Manual", 2D, 400);

    given(equipmentRepository.findById(id)).willReturn(Optional.of(expected));

    final var actual = equipmentService.getEquipmentById(id);
    assertThat(actual, is(expected));

    then(equipmentRepository).should(only()).findById(eq(id));
  }

  @Test
  void getEquipmentById_given_correct_id_and_equipment_notFound() {
    final var id = 500L;
    final var exception = new EntityNotFoundException("equipment", id);
    final var message = exception.getMessage();

    given(equipmentRepository.findById(id)).willReturn(empty());

    final var actual =
        assertThrows(EntityNotFoundException.class, () -> equipmentService.getEquipmentById(id));
    assertThat(actual.getMessage(), is(message));

    then(equipmentRepository).should(only()).findById(eq(id));
  }

  @Test
  void getEquipments() {
    final var listEquipments =
        List.of(
            new Equipment(2L, "Base", "Petrol", "Manual", 3D, 500),
            new Equipment(3L, "Extended", "Diesel", "Automate", 5D, 1100));
    final var expected =
        List.of(
            new EquipmentDTO(2L, "Base", "Petrol", "Manual", 3D, 500),
            new EquipmentDTO(3L, "Extended", "Diesel", "Automate", 5D, 1100));

    given(equipmentRepository.findAll()).willReturn(listEquipments);
    given(equipmentMapper.toDtos(listEquipments)).willReturn(expected);

    final var actual = equipmentService.getEquipments();
    assertThat(actual, is(expected));

    then(equipmentRepository).should(only()).findAll();
    then(equipmentMapper).should(only()).toDtos(argThat(listEquipments::equals));
  }
}
