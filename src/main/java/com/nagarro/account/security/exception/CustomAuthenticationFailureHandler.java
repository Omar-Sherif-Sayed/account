package com.nagarro.account.security.exception;

import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if (exception.getMessage().equalsIgnoreCase("User is disabled"))
            throw BaseException.builder().errorCode(ErrorCode.AUTH_USER_DISABLED).build();
        else if (exception.getMessage().equalsIgnoreCase("User account has expired"))
            throw BaseException.builder().errorCode(ErrorCode.AUTH_TOKEN_EXPIRED).build();
        else
            throw BaseException.builder().errorCode(ErrorCode.AUTH_WRONG_USER_PASSWORD).build();
    }

}
