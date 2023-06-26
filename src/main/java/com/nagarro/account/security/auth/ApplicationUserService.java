package com.nagarro.account.security.auth;

import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
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

            if (user == null)
                throw new UsernameNotFoundException("No user found with username: " + username);

            return new ApplicationUser(user);
        } catch (Exception e) {
            log.error("Error in ApplicationUserService: ", e);
            if (e instanceof BadCredentialsException)
                throw BaseException.builder().errorCode(ErrorCode.AUTH_WRONG_CREDENTIALS).build();
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
    }

}
