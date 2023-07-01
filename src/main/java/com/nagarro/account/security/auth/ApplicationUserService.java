package com.nagarro.account.security.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final InMemoryUserDetailsManager userDetailsManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            var user = userDetailsManager.loadUserByUsername(username);

            return new ApplicationUser(user);
        } catch (Exception e) {
            log.error("Error in ApplicationUserService: ", e);
            throw new UsernameNotFoundException("No user found with username");
        }
    }

}
