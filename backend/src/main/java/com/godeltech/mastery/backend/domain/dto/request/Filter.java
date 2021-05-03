package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

@Data
public class Filter {

  private Long modelId;
  private String vin;
  private String lastname;
  private Integer specificYear;
  private Integer until;
  private Integer from;
}
