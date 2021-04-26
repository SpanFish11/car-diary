package com.godeltech.mastery.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

  private Long id;
  private ModelDTO model;
  private BrandDTO brand;
  private Integer year;
  private String photoUrl;
  private String vin;
  private Integer mileage;
}
