package com.nagarro.account.security.exception;

import com.nagarro.account.exception.BaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ImportAutoConfiguration
class CustomAuthenticationFailureHandlerTest {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @CsvSource({
            "'User is disabled', 'BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_USER_DISABLED, detail=null)'",
            "'User account has expired', 'BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_TOKEN_EXPIRED, detail=null)'",
            "'any thing else', 'BaseException(httpStatus=406 NOT_ACCEPTABLE, errorCode=AUTH_WRONG_USER_PASSWORD, detail=null)'",
    })
    void itShouldTestAuthenticationFailure(String disabledExceptionMessage,
                                           String expectedToString) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException exception = new DisabledException(disabledExceptionMessage);

        assertThatThrownBy(() -> customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception))
                .isInstanceOf(BaseException.class)
                .hasToString(expectedToString);
    }

}
