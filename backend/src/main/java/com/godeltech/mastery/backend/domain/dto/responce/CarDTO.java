package com.godeltech.mastery.backend.domain.dto.responce;

import com.godeltech.mastery.backend.domain.entity.Guarantee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

  private Long id;
  private BrandDTO brand;
  private ModelDTO model;
  private Integer year;
  private String photoUrl;
  private String vin;
  private Integer mileage;
  private Boolean ours;
  private ClientDTO client;
  private EquipmentDTO equipment;
  private Boolean used;
  private BigDecimal price;
  private Guarantee guarantee;
  private List<ServiceOperationRecordDTO> serviceOperations;
}
