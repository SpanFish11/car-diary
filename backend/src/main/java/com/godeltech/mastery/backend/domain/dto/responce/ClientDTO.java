package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
}
