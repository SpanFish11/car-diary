package com.godeltech.mastery.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientCreateRequest {

  @NotBlank(message = "First Name is mandatory")
  private String firstName;

  @NotBlank(message = "Last Name is mandatory")
  private String lastName;

  @Email
  @NotBlank(message = "Email is mandatory")
  private String email;
}
