package com.godeltech.mastery.backend.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientCreateRequest {

  @NotBlank(message = "First Name is mandatory")
  @Size(min = 1, max = 255)
  private String firstName;

  @NotBlank(message = "Last Name is mandatory")
  @Size(min = 1, max = 255)
  private String lastName;

  @Email
  @NotBlank(message = "Email is mandatory")
  private String email;
}
