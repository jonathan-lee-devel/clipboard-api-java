package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final JavaMailSender javaMailSender;

  @Override
  public SimpleMailMessage sendRegistrationVerificationEmail(final String targetEmail,
      final String tokenValue) {
    final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(targetEmail);
    simpleMailMessage.setSubject("Clipboard E-mail Verification");
    simpleMailMessage.setText(
        String.format("Please click the following link to verify your account: %s",
            String.format("https://clipboard.jonathanlee.io/register/confirm/%s", tokenValue)))
    ;

    try {
      this.javaMailSender.send(simpleMailMessage);
      log.info("Registration verification e-mail sent to: {}", targetEmail);
      return simpleMailMessage;
    } catch (MailException mailException) {
      log.error("Registration verification e-mail could not be sent: {}",
          mailException.getMessage());
      return null;
    }
  }

  @Override
  public SimpleMailMessage sendPasswordResetEmail(final String targetEmail,
      final String tokenValue) {
    final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(targetEmail);
    simpleMailMessage.setSubject("Clipboard Password Reset");
    simpleMailMessage.setText(
        String.format("Please click the following link to reset your password: %s",
            String.format("https://clipboard.jonathanlee.io/password/reset/%s", tokenValue))
    );

    try {
      this.javaMailSender.send(simpleMailMessage);
      return simpleMailMessage;
    } catch (MailException mailException) {
      log.error("Password reset e-mail could not be sent: {}", mailException.getMessage());
      return null;
    }
  }

}
