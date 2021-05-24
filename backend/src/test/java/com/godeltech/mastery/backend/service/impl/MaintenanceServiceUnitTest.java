package com.godeltech.mastery.backend.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.only;

import com.godeltech.mastery.backend.domain.dto.responce.MaintenanceDTO;
import com.godeltech.mastery.backend.domain.entity.Maintenance;
import com.godeltech.mastery.backend.mapper.MaintenanceMapper;
import com.godeltech.mastery.backend.repository.MaintenanceRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class MaintenanceServiceUnitTest {

  @Mock MaintenanceRepository maintenanceRepository;
  @Mock MaintenanceMapper maintenanceMapper;

  @InjectMocks MaintenanceServiceImpl maintenanceService;

  @Test
  void getAllMaintenance() {
    final Maintenance maintenance = new Maintenance();
    maintenance.setId(1L);
    maintenance.setOperationNumber("TO-2(full)");
    maintenance.setMileage(30_000);
    maintenance.setTimeInterval(24);
    final List<Maintenance> maintenances = List.of(maintenance);
    final MaintenanceDTO maintenanceDTO =
        new MaintenanceDTO(1L, "TO-2(full)", 30_000, 24, null, null);
    final List<MaintenanceDTO> expected = List.of();

    given(maintenanceRepository.findAll()).willReturn(maintenances);
    given(maintenanceMapper.toDTOList(maintenances)).willReturn(expected);

    final List<MaintenanceDTO> actual = maintenanceService.getAllMaintenance();
    assertThat(actual, is(expected));

    then(maintenanceRepository).should(only()).findAll();
    then(maintenanceMapper).should(atLeastOnce()).toDTOList(refEq(maintenances));
  }
}
