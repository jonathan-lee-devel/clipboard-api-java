package io.jonathanlee.registrationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationDto {

  @NotNull
  @Email
  private String email;

  @NotNull
  @Size(min = Constraints.MIN_NAME_LENGTH, max = Constraints.MAX_NAME_LENGTH)
  private String firstName;

  @NotNull
  @Size(min = Constraints.MIN_NAME_LENGTH, max = Constraints.MAX_NAME_LENGTH)
  private String lastName;

  @NotNull
  @Size(min = Constraints.MIN_PASSWORD_LENGTH, max = Constraints.MAX_PASSWORD_LENGTH)
  private String password;

  @NotNull
  @Size(min = Constraints.MIN_PASSWORD_LENGTH, max = Constraints.MAX_PASSWORD_LENGTH)
  private String confirmPassword;

}
