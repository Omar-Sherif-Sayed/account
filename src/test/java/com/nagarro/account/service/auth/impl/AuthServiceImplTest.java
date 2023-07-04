package com.nagarro.account.service.auth.impl;

import com.nagarro.account.entity.auth.UserCacheEntity;
import com.nagarro.account.exception.BaseException;
import com.nagarro.account.model.request.auth.LoginRequest;
import com.nagarro.account.model.response.auth.LoginResponse;
import com.nagarro.account.repository.auth.UserCacheRepository;
import com.nagarro.account.security.config.jwt.JwtConfig;
import com.nagarro.account.security.util.JwtUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class AuthServiceImplTest {

    @Value("${security.user.password}")
    private String userPassword;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        userCacheRepository.deleteAll();
    }

    @Test
    void isShouldTestSuccessLogin() {
        // given
        LoginRequest authenticationRequest = new LoginRequest();
        authenticationRequest.setUsername("user");
        authenticationRequest.setPassword(userPassword);

        // when
        LoginResponse loginResponse = authService.login(authenticationRequest);

        // then
        assertThat(loginResponse.getRole())
                .isEqualTo("ROLE_USER");

        assertThat(loginResponse.getToken())
                .startsWith("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV");

        Optional<UserCacheEntity> user = userCacheRepository.findById("user");

        assertThat(user)
                .isPresent();

        UserCacheEntity userCacheEntity = user.orElse(null);

        assert userCacheEntity != null;

        assertThat(userCacheEntity.getUsername())
                .isEqualTo("user");

        assertThat(userCacheEntity.getToken())
                .startsWith("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV");
    }

    @Test
    void isShouldTestThrownAlreadyLoggedIn() {
        // given
        LoginRequest authenticationRequest = new LoginRequest();
        authenticationRequest.setUsername("user");
        authenticationRequest.setPassword(userPassword);

        // when
        authService.login(authenticationRequest);

        // then
        String expected = "BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_USER_ALREADY_LOGGED_IN, detail=null)";

        assertThatThrownBy(() -> authService.login(authenticationRequest))
                .isInstanceOf(BaseException.class)
                .hasToString(expected);

    }

    @Test
    void isShouldTestThrownHasWrongCredentials() {
        // given
        LoginRequest authenticationRequest = new LoginRequest();
        authenticationRequest.setUsername("user");
        authenticationRequest.setPassword(userPassword + "-wrong-password");

        // then
        String expected = "BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_WRONG_CREDENTIALS, detail=null)";

        assertThatThrownBy(() -> authService.login(authenticationRequest))
                .isInstanceOf(BaseException.class)
                .hasToString(expected);

    }

    @Test
    void itShouldTestSuccessfulLogout() {

        // given
        String userUsername = "user";
        Authentication authentication = new UsernamePasswordAuthenticationToken(userUsername, userPassword);
        var authenticate = authenticationManager.authenticate(authentication);
        String token = jwtUtil.generateJwtToken(authenticate);

        LoginRequest authenticationRequest = new LoginRequest();
        authenticationRequest.setUsername(userUsername);
        authenticationRequest.setPassword(userPassword);

        // when
        authService.login(authenticationRequest);
        Boolean isLoggedOut = authService.logout(jwtConfig.getTokenPrefix() + token);

        // then
        assertThat(isLoggedOut)
                .isTrue();
    }

    @Test
    void itShouldTestFailedLogout() {

        // given
        String wrongJwt = "wrongValue";

        // when
        Boolean isLoggedOut = authService.logout(wrongJwt);

        // then
        assertThat(isLoggedOut)
                .isFalse();
    }

}