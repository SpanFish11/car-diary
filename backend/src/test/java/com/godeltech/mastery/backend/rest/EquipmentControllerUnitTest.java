package com.godeltech.mastery.backend.rest;

import com.godeltech.mastery.backend.domain.dto.responce.EquipmentDTO;
import com.godeltech.mastery.backend.service.EquipmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.ResponseEntity.ok;

@ExtendWith(SpringExtension.class)
class EquipmentControllerUnitTest {

  @Mock private EquipmentService equipmentService;

  @InjectMocks private EquipmentController equipmentController;

  @Test
  void getAllEqu() {
    final var equipments =
        List.of(
            new EquipmentDTO(2L, "Base", "Petrol", "Manual", 3D, 500),
            new EquipmentDTO(3L, "Extended", "Diesel", "Automate", 5D, 1100));
    final ResponseEntity<List<EquipmentDTO>> expected = ok(equipments);

    given(equipmentService.getEquipments()).willReturn(equipments);

    final ResponseEntity<List<EquipmentDTO>> actual = equipmentController.getAllEqu();
    assertThat(actual, is(expected));

    then(equipmentService).should(only()).getEquipments();
  }
}
