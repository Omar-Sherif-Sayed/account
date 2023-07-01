package com.nagarro.account.security.exception;

import com.google.gson.Gson;
import com.nagarro.account.exception.enums.ErrorCode;
import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import com.nagarro.account.security.config.WebSecurityConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ImportAutoConfiguration({
        RedisAutoConfiguration.class,
        WebSecurityConfig.class
})
class CustomAccessDeniedHandlerTest {

    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ExceptionHandlerUtil exceptionHandlerUtil;

    @Value("${security.user.password}")
    private String userPassword;

    @BeforeEach
    void setUp() {
        this.accessDeniedHandler = new CustomAccessDeniedHandler(exceptionHandlerUtil);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleWhenAuthenticatedThenStatus403() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication authentication = new TestingAuthenticationToken("user", userPassword);
        request.setUserPrincipal(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        this.accessDeniedHandler.handle(request, response, null);

        String responseJson = new Gson().toJson(response.getContentAsString());

        assertThat(response.getStatus()).isEqualTo(403);
        assertThat(responseJson).contains(ErrorCode.AUTH_USER_UNAUTHORIZED.name());
    }

    @Test
    void handleWhenNotAuthenticatedThenStatus403AndResponseWithErrorCode() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication authentication = new TestingAuthenticationToken("user", userPassword);
        request.setUserPrincipal(authentication);

        this.accessDeniedHandler.handle(request, response, null);
        String responseJson = new Gson().toJson(response.getContentAsString());

        assertThat(response.getStatus()).isEqualTo(403);
        assertThat(responseJson).contains(ErrorCode.AUTH_USER_UNAUTHORIZED.name());
    }

}