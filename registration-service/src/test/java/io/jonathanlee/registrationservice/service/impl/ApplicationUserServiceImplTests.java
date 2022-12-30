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
import java.util.Optional;

class ApplicationUserServiceImplTests {

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
    void testFindByRegistrationVerificationToken() {
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
    void testFindByPasswordResetToken() {
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
    void testEnableUser() {
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

    @Test
    void testDeleteUser_success() {
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

        Mockito.when(this.applicationUserRepository.findById(applicationUser.getObjectId())).thenReturn(Optional.of(applicationUser));
        Mockito.doNothing().when(this.applicationUserRepository).delete(applicationUser);

        final ApplicationUser deletedUser = this.applicationUserService.deleteApplicationUser(applicationUser);
        Assertions.assertEquals(applicationUser, deletedUser);
    }

    @Test
    void testDeleteUser_failure() {
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

        Mockito.when(this.applicationUserRepository.findById(applicationUser.getObjectId())).thenReturn(Optional.empty());
        Mockito.doNothing().when(this.applicationUserRepository).delete(applicationUser);

        final ApplicationUser deletedUser = this.applicationUserService.deleteApplicationUser(applicationUser);
        Assertions.assertNotEquals(applicationUser, deletedUser);
        Assertions.assertNull(deletedUser);
    }

}
