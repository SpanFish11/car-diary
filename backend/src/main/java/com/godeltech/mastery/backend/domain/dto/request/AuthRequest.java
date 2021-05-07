package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthRequest {

  @NotBlank @Email private String email;

  @NotBlank private String password;
}
