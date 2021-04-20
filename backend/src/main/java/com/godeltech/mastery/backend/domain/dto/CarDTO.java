package com.godeltech.mastery.backend.domain.dto;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CarDTO {
  private Model model;
  private Brand brand;
  private LocalDate year;
  private String photoUrl;
  private String vin;
  private Integer mileage;
}
