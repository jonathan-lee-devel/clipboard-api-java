package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.model.ApplicationUser;
import io.jonathanlee.registrationservice.model.Token;
import io.jonathanlee.registrationservice.repository.ApplicationUserRepository;
import io.jonathanlee.registrationservice.service.ApplicationUserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;

public class ApplicationUserServiceImplTests {

    private ApplicationUserService applicationUserService;

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.applicationUserService = new ApplicationUserServiceImpl(this.applicationUserRepository);
    }

    @Test
    void testPersistApplicationUser() {
        final Token registrationVerificationToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final Token passwordResetToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final ApplicationUser applicationUser = new ApplicationUser(
                ObjectId.get(),
                "123",
                "test@mail.com",
                "password",
                "John",
                "Doe",
                false,
                registrationVerificationToken,
                passwordResetToken
        );
        Mockito.when(this.applicationUserRepository.save(ArgumentMatchers.any())).thenReturn(applicationUser);

        Assertions.assertEquals(applicationUser, this.applicationUserService.persistApplicationUser(applicationUser));
    }

    @Test
    public void testFindByRegistrationVerificationToken() {
        final Token registrationVerificationToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final Token passwordResetToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final ApplicationUser applicationUser = new ApplicationUser(
                ObjectId.get(),
                "123",
                "test@mail.com",
                "password",
                "John",
                "Doe",
                false,
                registrationVerificationToken,
                passwordResetToken
        );

        Mockito.when(this.applicationUserRepository.findByRegistrationVerificationToken(ArgumentMatchers.any())).thenReturn(applicationUser);

        Assertions.assertEquals(applicationUser, this.applicationUserService.findByRegistrationVerificationToken(registrationVerificationToken));
    }

    @Test
    public void testFindByPasswordResetToken() {
        final Token registrationVerificationToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final Token passwordResetToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final ApplicationUser applicationUser = new ApplicationUser(
                ObjectId.get(),
                "123",
                "test@mail.com",
                "password",
                "John",
                "Doe",
                false,
                registrationVerificationToken,
                passwordResetToken
        );

        Mockito.when(this.applicationUserRepository.findByPasswordResetToken(ArgumentMatchers.any())).thenReturn(applicationUser);

        Assertions.assertEquals(applicationUser, this.applicationUserService.findByPasswordResetToken(passwordResetToken));
    }

    @Test
    public void testEnableUser() {
        final Token registrationVerificationToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final Token passwordResetToken = new Token(
                ObjectId.get(),
                "123",
                "value",
                Instant.now()
        );
        final ApplicationUser applicationUser = new ApplicationUser(
                ObjectId.get(),
                "123",
                "test@mail.com",
                "password",
                "John",
                "Doe",
                false,
                registrationVerificationToken,
                passwordResetToken
        );

        Mockito.when(this.applicationUserRepository.save(applicationUser)).thenReturn(applicationUser);

        this.applicationUserService.enableUser(applicationUser);

        Assertions.assertTrue(this.applicationUserService.enableUser(applicationUser).isEnabled());
        Assertions.assertTrue(applicationUser.isEnabled());
    }

}
