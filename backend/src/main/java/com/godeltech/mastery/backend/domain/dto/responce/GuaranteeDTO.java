package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GuaranteeDTO {

  private LocalDate start;

  private LocalDate end;

  private Boolean extended;
}
