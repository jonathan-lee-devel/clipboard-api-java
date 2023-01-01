package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.dto.RegistrationDto;
import io.jonathanlee.registrationservice.dto.RegistrationStatusDto;
import io.jonathanlee.registrationservice.enums.RegistrationStatus;
import io.jonathanlee.registrationservice.model.ApplicationUser;
import io.jonathanlee.registrationservice.model.Token;
import io.jonathanlee.registrationservice.service.ApplicationUserService;
import io.jonathanlee.registrationservice.service.MailService;
import io.jonathanlee.registrationservice.service.RandomService;
import io.jonathanlee.registrationservice.service.RegistrationService;
import io.jonathanlee.registrationservice.service.TokenService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final RandomService randomService;

  private final PasswordEncoder passwordEncoder;

  private final TokenService tokenService;

  private final ApplicationUserService applicationUserService;

  private final MailService mailService;

  @Override
  public RegistrationStatusDto registerNewUser(final RegistrationDto registrationDto) {
    if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
      return new RegistrationStatusDto(RegistrationStatus.PASSWORDS_DO_NOT_MATCH);
    }

    final ApplicationUser newApplicationUser = new ApplicationUser(
        ObjectId.get(),
        this.randomService.generateNewId(),
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        this.tokenService.generateAndPersistNewValidToken(),
        this.tokenService.generateAndPersistNewExpiredToken()
    );

    this.applicationUserService.deleteDisabledApplicationUserByEmail(registrationDto.getEmail());

    final ApplicationUser savedApplicationUser = this.applicationUserService.persistApplicationUser(
        newApplicationUser);
    if (newApplicationUser.getId().equals(savedApplicationUser.getId())) {// If save was successful
      final SimpleMailMessage sentSimpleMailMessage = this.mailService.sendRegistrationVerificationEmail(
          savedApplicationUser.getEmail(),
          newApplicationUser.getRegistrationVerificationToken().getValue());
      if (sentSimpleMailMessage == null) {
        final ApplicationUser deletedApplicationUser = this.applicationUserService.deleteApplicationUser(
            savedApplicationUser);
        if (deletedApplicationUser == null) {
          log.error("Failed to delete application user with ID: {}",
              savedApplicationUser.getId());
        }
        return new RegistrationStatusDto(RegistrationStatus.FAILURE);
      }
      return new RegistrationStatusDto(RegistrationStatus.AWAITING_EMAIL_VERIFICATION);
    }

    return new RegistrationStatusDto(RegistrationStatus.FAILURE);
  }

  @Override
  public RegistrationStatusDto confirmNewUserRegistration(final String tokenValue) {
    final Token registrationVerificationToken = this.tokenService.findByTokenValue(tokenValue);
    if (registrationVerificationToken == null) {
      return new RegistrationStatusDto(RegistrationStatus.INVALID_TOKEN);
    }

    if (!registrationVerificationToken.getExpiryDate().isBefore(Instant.now())) {
      final ApplicationUser applicationUserToEnable = this.applicationUserService
          .findByRegistrationVerificationToken(registrationVerificationToken);
      final ApplicationUser updatedUser = this.applicationUserService.enableUser(
          applicationUserToEnable);
      this.tokenService.expireToken(registrationVerificationToken);
      if (updatedUser.isEnabled()) {
        return new RegistrationStatusDto(RegistrationStatus.SUCCESS);
      }
    } else {
      return new RegistrationStatusDto(RegistrationStatus.EMAIL_VERIFICATION_EXPIRED);
    }

    return new RegistrationStatusDto(RegistrationStatus.FAILURE);
  }

}
