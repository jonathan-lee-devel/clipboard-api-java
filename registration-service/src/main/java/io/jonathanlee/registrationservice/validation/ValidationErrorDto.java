package io.jonathanlee.registrationservice.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorDto {

  private String field;

  private String message;

}
