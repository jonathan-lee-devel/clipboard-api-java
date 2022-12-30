package io.jonathanlee.registrationservice.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    SimpleMailMessage sendRegistrationVerificationEmail(final String targetEmail, final String tokenValue);

    SimpleMailMessage sendPasswordResetEmail(final String targetEmail, final String tokenValue);

}
