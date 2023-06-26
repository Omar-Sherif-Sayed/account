package com.nagarro.account.security.exception;

import com.google.gson.Gson;
import com.nagarro.account.exception.BaseError;
import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LogManager.getLogger(CustomAccessDeniedHandler.class);

    private final ExceptionHandlerUtil exceptionHandlerUtil;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null)
            logger.log(Level.WARN, "User: {} attempted to access the protected URL: {}",
                    auth.getName(), request.getRequestURI());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        ResponseEntity<BaseError> baseErrorResponseEntity = exceptionHandlerUtil
                .commonReturn(BaseException
                        .builder()
                        .errorCode(ErrorCode.AUTH_USER_UNAUTHORIZED)
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .build());

        response.getWriter().println(new Gson().toJson(baseErrorResponseEntity.getBody()));
    }

}
