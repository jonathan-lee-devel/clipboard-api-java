package io.jonathanlee.registrationservice.controller;

import io.jonathanlee.registrationservice.dto.RegistrationDto;
import io.jonathanlee.registrationservice.dto.RegistrationStatusDto;
import io.jonathanlee.registrationservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<RegistrationStatusDto> registerNewUser(@RequestBody final RegistrationDto registrationDto) {
        final RegistrationStatusDto registrationStatusDto = this.registrationService.registerNewUser(registrationDto);

        switch(registrationStatusDto.getRegistrationStatus()) {
            case AWAITING_EMAIL_VERIFICATION -> {
                return ResponseEntity.status(HttpStatus.OK).body(registrationStatusDto);
            }
            case PASSWORDS_DO_NOT_MATCH -> {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registrationStatusDto);
            }
            default -> {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registrationStatusDto);
            }
        }
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            value = "/confirm/{tokenValue}"
    )
    ResponseEntity<RegistrationStatusDto> confirmNewUserRegistration(
            @PathVariable final String tokenValue) {
        final RegistrationStatusDto
                registrationStatusDto = this.registrationService.confirmNewUserRegistration(tokenValue);

        switch(registrationStatusDto.getRegistrationStatus()) {
            case SUCCESS -> {
                return ResponseEntity.status(HttpStatus.OK).body(registrationStatusDto);
            }
            case INVALID_TOKEN, EMAIL_VERIFICATION_EXPIRED -> {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(registrationStatusDto);
            }
            default -> {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registrationStatusDto);
            }
        }
    }

}
