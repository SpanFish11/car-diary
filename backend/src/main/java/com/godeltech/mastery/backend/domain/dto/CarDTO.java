package com.godeltech.mastery.backend.domain.dto;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
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
  private Model model;
  private Brand brand;
  private Integer year;
  private String photoUrl;
  private String vin;
  private Integer mileage;
}
