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
import java.time.ZonedDateTime;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;

class RegistrationServiceImplTests {

  @Mock
  private RandomService randomService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private TokenService tokenService;

  @Mock
  private ApplicationUserService applicationUserService;

  @Mock
  private MailService mailService;

  private RegistrationService registrationService;

  @BeforeEach
  void beforeEach() {
    MockitoAnnotations.openMocks(this);
    this.registrationService = new RegistrationServiceImpl(this.randomService,
        this.passwordEncoder, this.tokenService, this.applicationUserService, this.mailService);
  }

  @Test
  void testRegisterNewUser_passwordsDoNotMatch() {
    final RegistrationDto registrationDto = new RegistrationDto();
    registrationDto.setPassword("password");
    registrationDto.setConfirmPassword("other");

    final RegistrationStatusDto registrationStatusDto = this.registrationService.registerNewUser(
        registrationDto);

    Assertions.assertEquals(RegistrationStatus.PASSWORDS_DO_NOT_MATCH,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testRegisterNewUser_failToSendEmailSuccessfullyDeletedUser() {
    final RegistrationDto registrationDto = new RegistrationDto();
    registrationDto.setEmail("test@mail.com");
    registrationDto.setPassword("password");
    registrationDto.setConfirmPassword("password");
    registrationDto.setFirstName("John");
    registrationDto.setLastName("Doe");

    final Token registrationVerificationToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().plusMinutes(TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES)
            .toInstant()
    );

    final Token passwordResetToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().toInstant()
    );

    Mockito.when(this.randomService.generateNewId()).thenReturn("123");
    Mockito.when(this.tokenService.generateAndPersistNewValidToken())
        .thenReturn(registrationVerificationToken);
    Mockito.when(this.tokenService.generateAndPersistNewExpiredToken())
        .thenReturn(passwordResetToken);

    final ApplicationUser applicationUser = new ApplicationUser(
        ObjectId.get(),
        this.randomService.generateNewId(),
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        registrationVerificationToken,
        passwordResetToken
    );

    Mockito.when(this.applicationUserService.persistApplicationUser(ArgumentMatchers.any()))
        .thenReturn(applicationUser);

    Mockito.when(
        this.mailService.sendRegistrationVerificationEmail(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).thenReturn(null);

    Mockito.when(
        this.applicationUserService.deleteApplicationUser(applicationUser)
    ).thenReturn(applicationUser);

    final RegistrationStatusDto registrationStatusDto =
        this.registrationService.registerNewUser(registrationDto);

    Assertions.assertEquals(RegistrationStatus.FAILURE,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testRegisterNewUser_failToSendEmailFailedToDeleteUser() {
    final RegistrationDto registrationDto = new RegistrationDto();
    registrationDto.setEmail("test@mail.com");
    registrationDto.setPassword("password");
    registrationDto.setConfirmPassword("password");
    registrationDto.setFirstName("John");
    registrationDto.setLastName("Doe");

    final Token registrationVerificationToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().plusMinutes(TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES)
            .toInstant()
    );

    final Token passwordResetToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().toInstant()
    );

    Mockito.when(this.randomService.generateNewId()).thenReturn("123");
    Mockito.when(this.tokenService.generateAndPersistNewValidToken())
        .thenReturn(registrationVerificationToken);
    Mockito.when(this.tokenService.generateAndPersistNewExpiredToken())
        .thenReturn(passwordResetToken);

    final ApplicationUser applicationUser = new ApplicationUser(
        ObjectId.get(),
        this.randomService.generateNewId(),
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        registrationVerificationToken,
        passwordResetToken
    );

    Mockito.when(this.applicationUserService.persistApplicationUser(ArgumentMatchers.any()))
        .thenReturn(applicationUser);

    Mockito.when(
        this.mailService.sendRegistrationVerificationEmail(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).thenReturn(null);

    Mockito.when(
        this.applicationUserService.deleteApplicationUser(applicationUser)
    ).thenReturn(null);

    final RegistrationStatusDto registrationStatusDto =
        this.registrationService.registerNewUser(registrationDto);

    Assertions.assertEquals(RegistrationStatus.FAILURE,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testRegisterNewUser_failedToSaveUser() {
    final RegistrationDto registrationDto = new RegistrationDto();
    registrationDto.setEmail("test@mail.com");
    registrationDto.setPassword("password");
    registrationDto.setConfirmPassword("password");
    registrationDto.setFirstName("John");
    registrationDto.setLastName("Doe");

    final Token registrationVerificationToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().plusMinutes(TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES)
            .toInstant()
    );

    final Token passwordResetToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().toInstant()
    );

    Mockito.when(this.randomService.generateNewId()).thenReturn("123");
    Mockito.when(this.tokenService.generateAndPersistNewValidToken())
        .thenReturn(registrationVerificationToken);
    Mockito.when(this.tokenService.generateAndPersistNewExpiredToken())
        .thenReturn(passwordResetToken);

    final ApplicationUser applicationUser = new ApplicationUser(
        ObjectId.get(),
        this.randomService.generateNewId(),
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        registrationVerificationToken,
        passwordResetToken
    );

    final ApplicationUser otherApplicationUser = new ApplicationUser(
        ObjectId.get(),
        "other",
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        registrationVerificationToken,
        passwordResetToken
    );

    Mockito.when(this.applicationUserService.persistApplicationUser(ArgumentMatchers.any()))
        .thenReturn(otherApplicationUser);

    Mockito.when(
        this.mailService.sendRegistrationVerificationEmail(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).thenReturn(new SimpleMailMessage());

    Mockito.when(
        this.applicationUserService.deleteApplicationUser(applicationUser)
    ).thenReturn(applicationUser);

    final RegistrationStatusDto registrationStatusDto =
        this.registrationService.registerNewUser(registrationDto);

    Assertions.assertEquals(RegistrationStatus.FAILURE,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testRegisterNewUser_awaitingEmailVerification() {
    final RegistrationDto registrationDto = new RegistrationDto();
    registrationDto.setEmail("test@mail.com");
    registrationDto.setPassword("password");
    registrationDto.setConfirmPassword("password");
    registrationDto.setFirstName("John");
    registrationDto.setLastName("Doe");

    final Token registrationVerificationToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().plusMinutes(TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES)
            .toInstant()
    );

    final Token passwordResetToken = new Token(
        ObjectId.get(),
        "123",
        "456",
        ZonedDateTime.now().toInstant()
    );

    Mockito.when(this.randomService.generateNewId()).thenReturn("123");
    Mockito.when(this.tokenService.generateAndPersistNewValidToken())
        .thenReturn(registrationVerificationToken);
    Mockito.when(this.tokenService.generateAndPersistNewExpiredToken())
        .thenReturn(passwordResetToken);

    final ApplicationUser applicationUser = new ApplicationUser(
        ObjectId.get(),
        this.randomService.generateNewId(),
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        registrationVerificationToken,
        passwordResetToken
    );

    Mockito.when(this.applicationUserService.persistApplicationUser(ArgumentMatchers.any()))
        .thenReturn(applicationUser);

    Mockito.when(
        this.mailService.sendRegistrationVerificationEmail(ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString())).thenReturn(new SimpleMailMessage());

    Mockito.when(
        this.applicationUserService.deleteApplicationUser(applicationUser)
    ).thenReturn(applicationUser);

    final RegistrationStatusDto registrationStatusDto =
        this.registrationService.registerNewUser(registrationDto);

    Assertions.assertEquals(RegistrationStatus.AWAITING_EMAIL_VERIFICATION,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testConfirmNewUserRegistration_invalidToken() {
    final String tokenValue = "123";

    Mockito.when(this.tokenService.findByTokenValue(tokenValue)).thenReturn(null);

    final RegistrationStatusDto registrationStatusDto = this.registrationService.confirmNewUserRegistration(
        tokenValue);

    Assertions.assertEquals(RegistrationStatus.INVALID_TOKEN,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testConfirmNewUserRegistration_expiredToken() {
    final String tokenValue = "123";

    final Token expiredToken = new Token(
        ObjectId.get(),
        "123",
        tokenValue,
        ZonedDateTime.now().toInstant()
    );

    Mockito.when(this.tokenService.findByTokenValue(tokenValue)).thenReturn(expiredToken);

    final RegistrationStatusDto registrationStatusDto = this.registrationService.confirmNewUserRegistration(
        tokenValue);

    Assertions.assertEquals(RegistrationStatus.EMAIL_VERIFICATION_EXPIRED,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testConfirmNewUserRegistration_success() {
    final String tokenValue = "123";

    final Token validToken = new Token(
        ObjectId.get(),
        "123",
        tokenValue,
        ZonedDateTime.now().plusMinutes(TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES)
            .toInstant()
    );

    Mockito.when(this.tokenService.findByTokenValue(tokenValue)).thenReturn(validToken);

    final ApplicationUser disabledApplicationUser = new ApplicationUser(
        ObjectId.get(),
        "123",
        "test@mail.com",
        "password",
        "John",
        "Doe",
        false,
        validToken,
        validToken
    );

    final ApplicationUser enabledApplicationUser = new ApplicationUser(
        ObjectId.get(),
        "123",
        "test@mail.com",
        "password",
        "John",
        "Doe",
        true,
        validToken,
        validToken
    );

    Mockito.when(this.applicationUserService.findByRegistrationVerificationToken(validToken))
        .thenReturn(disabledApplicationUser);
    Mockito.when(this.applicationUserService.enableUser(disabledApplicationUser))
        .thenReturn(enabledApplicationUser);

    final RegistrationStatusDto registrationStatusDto = this.registrationService.confirmNewUserRegistration(
        tokenValue);

    Assertions.assertEquals(RegistrationStatus.SUCCESS,
        registrationStatusDto.getRegistrationStatus());
  }

  @Test
  void testConfirmNewUserRegistration_failure() {
    final String tokenValue = "123";

    final Token validToken = new Token(
        ObjectId.get(),
        "123",
        tokenValue,
        ZonedDateTime.now().plusMinutes(TokenServiceImpl.DEFAULT_TOKEN_EXPIRY_TIME_MINUTES)
            .toInstant()
    );

    Mockito.when(this.tokenService.findByTokenValue(tokenValue)).thenReturn(validToken);

    final ApplicationUser disabledApplicationUser = new ApplicationUser(
        ObjectId.get(),
        "123",
        "test@mail.com",
        "password",
        "John",
        "Doe",
        false,
        validToken,
        validToken
    );

    Mockito.when(this.applicationUserService.findByRegistrationVerificationToken(validToken))
        .thenReturn(disabledApplicationUser);
    Mockito.when(this.applicationUserService.enableUser(disabledApplicationUser))
        .thenReturn(disabledApplicationUser);

    final RegistrationStatusDto registrationStatusDto = this.registrationService.confirmNewUserRegistration(
        tokenValue);

    Assertions.assertEquals(RegistrationStatus.FAILURE,
        registrationStatusDto.getRegistrationStatus());
  }

}
