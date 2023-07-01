package com.nagarro.account.security.auth;

import com.nagarro.account.security.config.PasswordConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ImportAutoConfiguration({
        RedisAutoConfiguration.class,
        PasswordConfig.class
})
class ApplicationUserTest {

    @Autowired
    private ApplicationUser applicationUser;

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.admin.password}")
    private String adminPassword;

    @Value("${security.user.password}")
    private String userPassword;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldGetAminAuthorities() {
        // given
        String adminUsername = "admin";
        UserDetails admin = getUserDetails(adminUsername);

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        Collection<? extends GrantedAuthority> authorities = applicationUser.getAuthorities();

        assertThat(authorities.toArray()[0])
                .hasToString("ROLE_ADMIN");
    }

    @Test
    void itShouldGetUserAuthorities() {
        // given
        String userUsername = "user";
        UserDetails admin = getUserDetails(userUsername);

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        Collection<? extends GrantedAuthority> authorities = applicationUser.getAuthorities();

        assertThat(authorities.toArray()[0])
                .hasToString("ROLE_USER");
    }

    @Test
    void itShouldGetAminPassword() {
        // given
        UserDetails admin = getUserDetails("admin");

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        String password = applicationUser.getPassword();
        assertThat(passwordEncoder.matches(adminPassword, password))
                .isTrue();

    }

    @Test
    void itShouldGetUserPassword() {
        // given
        UserDetails user = getUserDetails("user");

        // when
        applicationUser = new ApplicationUser(user);

        // then
        String password = applicationUser.getPassword();
        assertThat(passwordEncoder.matches(userPassword, password))
                .isTrue();

    }

    @Test
    void itShouldGetAdminUsername() {
        // given
        String adminUsername = "admin";
        UserDetails admin = getUserDetails(adminUsername);

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        String username = applicationUser.getUsername();
        assertThat(username)
                .isEqualTo(adminUsername);
    }

    @Test
    void itShouldGetUserUsername() {
        // given
        String userUsername = "admin";
        UserDetails admin = getUserDetails(userUsername);

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        String username = applicationUser.getUsername();
        assertThat(username)
                .isEqualTo(userUsername);
    }

    @Test
    void isAccountNonExpired() {
        assertThat(applicationUser.isAccountNonExpired())
                .isTrue();
    }

    @Test
    void isAccountNonLocked() {
        assertThat(applicationUser.isAccountNonLocked())
                .isTrue();
    }

    @Test
    void isCredentialsNonExpired() {
        assertThat(applicationUser.isCredentialsNonExpired())
                .isTrue();
    }

    @Test
    void isEnabled() {
        assertThat(applicationUser.isEnabled())
                .isTrue();
    }

    @Test
    void itShouldGetAdminUser() {
        // given
        String adminUsername = "admin";
        UserDetails admin = getUserDetails(adminUsername);

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        UserDetails adminFromGetUserMethod = applicationUser.getUser();

        assertThat(adminFromGetUserMethod)
                .isEqualTo(admin);
    }

    @Test
    void itShouldGetUserUser() {
        // given
        String userUsername = "user";
        UserDetails admin = getUserDetails(userUsername);

        // when
        applicationUser = new ApplicationUser(admin);

        // then
        UserDetails userFromGetUserMethod = applicationUser.getUser();

        assertThat(userFromGetUserMethod)
                .isEqualTo(admin);
    }

    private UserDetails getUserDetails(String userUsername) {
        return userDetailsManager.loadUserByUsername(userUsername);
    }

}