package io.jonathanlee.registrationservice.service;

import io.jonathanlee.registrationservice.model.ApplicationUser;
import io.jonathanlee.registrationservice.model.Token;

public interface ApplicationUserService {

    ApplicationUser persistApplicationUser(final ApplicationUser applicationUser);

    ApplicationUser findByRegistrationVerificationToken(final Token token);

    ApplicationUser findByPasswordResetToken(final Token token);

    ApplicationUser enableUser(final ApplicationUser applicationUser);

    ApplicationUser deleteApplicationUser(final ApplicationUser applicationUser);

}
