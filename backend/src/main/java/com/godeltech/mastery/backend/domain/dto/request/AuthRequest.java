package com.godeltech.mastery.backend.domain.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

  @NotBlank @Email private String email;

  @NotBlank private String password;
}
