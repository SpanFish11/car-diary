package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.Data;

@Data
public class EquipmentDTO {

  private Long id;
  private String name;
  private String engineType;
  private String transmissionType;
  private Double engineSize;
  private Integer horsePower;
}
