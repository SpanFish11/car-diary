package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

  private String token;
}
