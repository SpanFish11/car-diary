package com.godeltech.mastery.backend.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.ResponseEntity.ok;

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDTO;
import com.godeltech.mastery.backend.service.MaintenanceService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MaintenanceControllerUnitTest {

  @Mock private MaintenanceService maintenanceService;

  @InjectMocks private MaintenanceController maintenanceController;

  @Test
  void getAllMaintenances() {
    final var maintenances =
        List.of(
            new MaintenanceDTO(2L, "To-full", 23415, 24, null, null),
            new MaintenanceDTO(4L, "TO-1", 15937, 12, null, null));
    final ResponseEntity<List<MaintenanceDTO>> expected = ok(maintenances);

    given(maintenanceService.getAllMaintenance()).willReturn(maintenances);

    final ResponseEntity<List<MaintenanceDTO>> actual = maintenanceController.getAllMaintenances();
    assertThat(actual, is(expected));

    then(maintenanceService).should(only()).getAllMaintenance();
  }
}
