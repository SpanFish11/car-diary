package com.godeltech.mastery.backend.domain.dto;

import com.godeltech.mastery.backend.domain.entity.Brand;
import com.godeltech.mastery.backend.domain.entity.Model;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@Data
public class CarCreateRequest {

  private Model model;
  private Brand brand;
  private LocalDate year;
  private MultipartFile photo;
  // add regex pattern
  private String vin;
  private Integer mileage;
}
