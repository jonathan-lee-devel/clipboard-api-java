package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.service.MailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class MailServiceImplTests {

    private MailService mailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.mailService = new MailServiceImpl(this.javaMailSender);
    }

    @Test
    void testSendRegistrationVerificationEmail_successfullySent() {
        final String targetEmail = "test@mail.com";
        final String tokenValue = "123";

        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Clipboard E-mail Verification");
        simpleMailMessage.setText(
                String.format("Please click the following link to verify your account: %s",
                        String.format("https://clipboard.jonathanlee.io/register/confirm/%s", tokenValue))
        );

        Mockito.doNothing().when(this.javaMailSender).send(simpleMailMessage);

        final SimpleMailMessage sentSimpleMailMessage = this.mailService.sendRegistrationVerificationEmail(targetEmail, tokenValue);

        Assertions.assertEquals(simpleMailMessage, sentSimpleMailMessage);
    }

    @Test
    void testSendRegistrationVerificationEmail_unsuccessfullySent() {
        final String targetEmail = "test@mail.com";
        final String tokenValue = "123";

        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Clipboard E-mail Verification");
        simpleMailMessage.setText(
                String.format("Please click the following link to verify your account: %s",
                        String.format("https://clipboard.jonathanlee.io/register/confirm/%s", tokenValue))
        );

        final String message = "Could not send mail";
        final Exception mailException = new MailException(message) {
            @Override
            public String getMessage() {
                return message;
            }
        };

        Mockito.doThrow(mailException).when(this.javaMailSender).send(simpleMailMessage);

        SimpleMailMessage sentSimpleMailMessage = simpleMailMessage;
        Exception caughtException = null;
        try {
            sentSimpleMailMessage = this.mailService.sendRegistrationVerificationEmail(targetEmail, tokenValue);
        } catch (MailException thrownMailException) {
            caughtException = thrownMailException;
        }

        Assertions.assertNull(sentSimpleMailMessage);
        Assertions.assertNotEquals(simpleMailMessage, sentSimpleMailMessage);
        Assertions.assertNull(caughtException);
    }

    @Test
    void testSendPasswordResetEmail_successfullySent() {
        final String targetEmail = "test@mail.com";
        final String tokenValue = "123";

        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Clipboard Password Reset");
        simpleMailMessage.setText(
                String.format("Please click the following link to reset your password: %s",
                        String.format("https://clipboard.jonathanlee.io/password/reset/%s", tokenValue))
        );

        Mockito.doNothing().when(this.javaMailSender).send(simpleMailMessage);

        final SimpleMailMessage sentSimpleMailMessage = this.mailService.sendPasswordResetEmail(targetEmail, tokenValue);

        Assertions.assertEquals(simpleMailMessage, sentSimpleMailMessage);
    }

    @Test
    void testSendPasswordResetEmail_unsuccessfullySent() {
        final String targetEmail = "test@mail.com";
        final String tokenValue = "123";

        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Clipboard Password Reset");
        simpleMailMessage.setText(
                String.format("Please click the following link to reset your password: %s",
                        String.format("https://clipboard.jonathanlee.io/password/reset/%s", tokenValue))
        );

        final String message = "Could not send mail";
        final Exception mailException = new MailException(message) {
            @Override
            public String getMessage() {
                return message;
            }
        };

        Mockito.doThrow(mailException).when(this.javaMailSender).send(simpleMailMessage);

        SimpleMailMessage sentSimpleMailMessage = simpleMailMessage;
        Exception caughtException = null;
        try {
            sentSimpleMailMessage = this.mailService.sendPasswordResetEmail(targetEmail, tokenValue);
        } catch (MailException thrownMailException) {
            caughtException = thrownMailException;
        }

        Assertions.assertNull(sentSimpleMailMessage);
        Assertions.assertNotEquals(simpleMailMessage, sentSimpleMailMessage);
        Assertions.assertNull(caughtException);
    }

}
