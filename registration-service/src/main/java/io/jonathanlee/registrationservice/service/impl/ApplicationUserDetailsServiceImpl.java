package io.jonathanlee.registrationservice.service.impl;

import io.jonathanlee.registrationservice.model.ApplicationUser;
import io.jonathanlee.registrationservice.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsServiceImpl implements UserDetailsService {

  private final ApplicationUserRepository applicationUserRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final ApplicationUser applicationUser = this.applicationUserRepository.findByEmail(username);
    if (applicationUser == null) {
      throw new UsernameNotFoundException("User not found for username: " + username);
    }
    return applicationUser;
  }

}
