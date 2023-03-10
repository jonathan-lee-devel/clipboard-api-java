package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.model.ApplicationUser;
import io.jonathanlee.registrationservice.model.Token;
import io.jonathanlee.registrationservice.repository.ApplicationUserRepository;
import io.jonathanlee.registrationservice.service.ApplicationUserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

  private final ApplicationUserRepository applicationUserRepository;

  @Override
  public ApplicationUser persistApplicationUser(final ApplicationUser applicationUser) {
    return this.applicationUserRepository.save(applicationUser);
  }

  @Override
  public ApplicationUser findByRegistrationVerificationToken(final Token token) {
    return this.applicationUserRepository.findByRegistrationVerificationToken(token);
  }

  @Override
  public ApplicationUser findByPasswordResetToken(final Token token) {
    return this.applicationUserRepository.findByPasswordResetToken(token);
  }

  @Override
  public ApplicationUser enableUser(final ApplicationUser applicationUser) {
    applicationUser.setEnabled(true);
    return this.applicationUserRepository.save(applicationUser);
  }

  @Override
  public ApplicationUser deleteDisabledApplicationUserByEmail(final String email) {
    final ApplicationUser applicationUserToDelete =
        this.applicationUserRepository.findByEmail(email);
    if (applicationUserToDelete != null && !applicationUserToDelete.isEnabled()) {
      this.applicationUserRepository.deleteById(applicationUserToDelete.getObjectId());
    }

    return applicationUserToDelete;
  }


  @Override
  public ApplicationUser deleteApplicationUser(ApplicationUser applicationUser) {
    final Optional<ApplicationUser> applicationUserToDelete = this.applicationUserRepository.findById(
        applicationUser.getObjectId());
    if (applicationUserToDelete.isPresent()) {
      this.applicationUserRepository.delete(applicationUser);
      return applicationUser;
    } else {
      return null;
    }
  }

}
