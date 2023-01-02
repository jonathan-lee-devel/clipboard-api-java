package io.jonathanlee.registrationservice.validation;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorsContainerDto {

  private Collection<ValidationErrorDto> errors;

}
