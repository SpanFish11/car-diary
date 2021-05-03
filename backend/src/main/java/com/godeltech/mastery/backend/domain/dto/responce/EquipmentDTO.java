package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EquipmentDTO {

  private Long id;
  private String name;
  private String engineType;
  private String transmissionType;
  private Double engineSize;
  private Integer horsePower;
}
