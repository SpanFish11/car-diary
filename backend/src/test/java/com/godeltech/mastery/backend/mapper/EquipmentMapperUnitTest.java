package com.godeltech.mastery.backend.mapper;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.domain.entity.Equipment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mapstruct.factory.Mappers.getMapper;

@ExtendWith(SpringExtension.class)
class EquipmentMapperUnitTest {

  private final EquipmentMapper equipmentMapper = getMapper(EquipmentMapper.class);

  @Test
  void toDto() {
    final var equipment = new Equipment(1L, "Luxury", "Petrol", "Manual", 2D, 400);

    final var expected = new EquipmentDTO(1L, "Luxury", "Petrol", "Manual", 2D, 400);

    final var actual = equipmentMapper.toDto(equipment);

    assertThat(actual, is(expected));
  }

  @Test
  void toDtos() {
    final var listEquipments =
        List.of(
            new Equipment(2L, "Base", "Petrol", "Manual", 3D, 500),
            new Equipment(3L, "Extended", "Diesel", "Automate", 5D, 1100));

    final var expected =
        List.of(
            new EquipmentDTO(2L, "Base", "Petrol", "Manual", 3D, 500),
            new EquipmentDTO(3L, "Extended", "Diesel", "Automate", 5D, 1100));

    final var actual = equipmentMapper.toDtos(listEquipments);

    assertThat(actual, is(expected));
  }
}
