package com.nagarro.account.security.exception;

import com.nagarro.account.exception.BaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ImportAutoConfiguration
class CustomAuthenticationFailureHandlerTest {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Value("${security.user.password}")
    private String userPassword;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldTestUserIsDisabledAuthenticationFailure() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException exception = new DisabledException("User is disabled");

        assertThatThrownBy(() -> customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception))
                .isInstanceOf(BaseException.class)
                .hasToString("BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_USER_DISABLED, detail=null)");
    }

    @Test
    void itShouldTestUserAccountHasExpiredAuthenticationFailure() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException exception = new DisabledException("User account has expired");

        assertThatThrownBy(() -> customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception))
                .isInstanceOf(BaseException.class)
                .hasToString("BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_TOKEN_EXPIRED, detail=null)");
    }

    @Test
    void itShouldTestUserHasAnyExceptionAuthenticationFailure() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException exception = new DisabledException("any thing else");

        assertThatThrownBy(() -> customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception))
                .isInstanceOf(BaseException.class)
                .hasToString("BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_WRONG_USER_PASSWORD, detail=null)");
    }

}
