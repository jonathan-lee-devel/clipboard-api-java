package io.jonathanlee.registrationservice.service;

public interface MailService {

    void sendRegistrationVerificationEmail(final String targetEmail, final String tokenValue);

    void sendPasswordResetEmail(final String targetEmail, final String tokenValue);

}
