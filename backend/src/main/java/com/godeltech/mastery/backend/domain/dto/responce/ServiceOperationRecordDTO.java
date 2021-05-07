package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOperationRecordDTO {

  private Long id;

  private String serviceOperationNumber;

  private LocalDate date;

  private Integer mileage;

  private List<ServiceWorkDTO> serviceWorks;

  private List<ChangablePartDTO> changableParts;
}
