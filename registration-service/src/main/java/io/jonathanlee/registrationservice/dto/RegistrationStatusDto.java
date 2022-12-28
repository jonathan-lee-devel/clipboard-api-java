package io.jonathanlee.registrationservice.dto;

import io.jonathanlee.registrationservice.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationStatusDto {

    private RegistrationStatus registrationStatus;

}
