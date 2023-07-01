package com.nagarro.account.security.auth;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ImportAutoConfiguration
class ApplicationUserServiceTest {

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldLoadAdminByUsername() {
        // given
        UserDetails admin = userDetailsManager.loadUserByUsername("admin");

        // when

        // then
        assertThat(applicationUserService.loadUserByUsername("admin"))
                .isInstanceOf(UserDetails.class);

        assertThat(applicationUserService.loadUserByUsername("admin").getUsername())
                .isEqualTo(admin.getUsername());

    }

    @Test
    void itShouldLoadUserByUsername() {
        // given
        UserDetails user = userDetailsManager.loadUserByUsername("user");

        // when

        // then
        assertThat(applicationUserService.loadUserByUsername("user"))
                .isInstanceOf(UserDetails.class);

        assertThat(applicationUserService.loadUserByUsername("user").getUsername())
                .isEqualTo(user.getUsername());

    }

    @Test
    @DisplayName("Test in case login with a wrong username")
    void itShouldNotLoadUserByUsername() {
        // given
        String wrongUsername = "wrong-user-name";

        // when

        // then
        assertThatThrownBy(() -> applicationUserService.loadUserByUsername(wrongUsername))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("No user found with username");


    }

}