package io.jonathanlee.registrationservice.service;

import io.jonathanlee.registrationservice.dto.RegistrationDto;
import io.jonathanlee.registrationservice.dto.RegistrationStatusDto;

public interface RegistrationService {

    RegistrationStatusDto registerNewUser(final RegistrationDto registrationDto);

    RegistrationStatusDto confirmNewUserRegistration(final String tokenValue);

}
