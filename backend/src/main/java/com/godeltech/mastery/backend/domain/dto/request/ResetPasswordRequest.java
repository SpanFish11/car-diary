package com.godeltech.mastery.backend.domain.dto.request;

import com.godeltech.mastery.backend.validator.PasswordValueMatch;
import com.godeltech.mastery.backend.validator.ValidPassword;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@PasswordValueMatch.List({
  @PasswordValueMatch(
      field = "newPassword",
      fieldMatch = "newPasswordConfirm",
      message = "Passwords do not match!")
})
@Data
public class ResetPasswordRequest {

  @NotBlank(message = "Old Password is mandatory")
  private String oldPassword;

  @NotBlank(message = "New Password is mandatory")
  @ValidPassword
  private String newPassword;

  @NotBlank(message = "New Confirm Password is mandatory")
  private String newPasswordConfirm;
}
