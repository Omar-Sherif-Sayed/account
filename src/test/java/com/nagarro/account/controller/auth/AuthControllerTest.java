package com.nagarro.account.controller.auth;

import com.nagarro.account.model.request.auth.LoginRequest;
import com.nagarro.account.model.response.BaseResponse;
import com.nagarro.account.model.response.auth.LoginResponse;
import com.nagarro.account.service.auth.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldTestSuccessfulLogin() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        LoginResponse loginResponse = LoginResponse.builder().token("tokenValue").role("ROLE_ADMIN").build();

        when(authService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        LoginRequest loginRequest = new LoginRequest();

        ResponseEntity<BaseResponse<LoginResponse>> responseEntity = authController.login(loginRequest);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getResponse().getToken()).isEqualTo("tokenValue");
        assertThat(responseEntity.getBody().getResponse().getRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void itShouldTestSuccessfulLogout() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(authService.logout(any(String.class))).thenReturn(true);

        ResponseEntity<BaseResponse<Boolean>> responseEntity = authController.logout("tokenValue");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getResponse())
                .isEqualTo(Boolean.TRUE);
    }

}