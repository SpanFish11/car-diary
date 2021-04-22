package com.godeltech.mastery.backend.domain.dto;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {

  private Long id;
  private Model model;
  private Brand brand;
  private Integer year;
  private String photoUrl;
  private String vin;
  private Integer mileage;
}
