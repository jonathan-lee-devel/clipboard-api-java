package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendRegistrationVerificationEmail(final String targetEmail, final String tokenValue) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Clipboard E-mail Verification");
        simpleMailMessage.setText(
                String.format("Please click the following link to verify your account: %s",
                        String.format("https://clipboard.jonathanlee.io/register/confirm/%s", tokenValue)))
        ;

        this.javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendPasswordResetEmail(final String targetEmail, final String tokenValue) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(targetEmail);
        simpleMailMessage.setSubject("Clipboard Password Reset");
        simpleMailMessage.setText(
                String.format("Please click the following link to reset your password: %s",
                        String.format("https://clipboard.jonathanlee.io/password/reset/%s", tokenValue))
        );

        this.javaMailSender.send(simpleMailMessage);
    }

}
