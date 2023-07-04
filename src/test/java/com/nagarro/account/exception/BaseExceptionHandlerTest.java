package com.nagarro.account.exception;

import com.nagarro.account.exception.enums.ErrorCode;
import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseExceptionHandlerTest {

    @Autowired
    private BaseExceptionHandler baseExceptionHandler;

    @Autowired
    private ExceptionHandlerUtil exceptionHandlerUtil;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldTestBaseExceptionHandler() {
        // given
        BaseException baseException = BaseException
                .builder()
                .errorCode(ErrorCode.AUTH_USER_UNAUTHORIZED)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .build();

        // when
        ResponseEntity<BaseError> baseErrorResponseEntity = baseExceptionHandler.baseExceptionHandler(baseException);

        // then
        assertThat(baseErrorResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.UNAUTHORIZED);

        BaseError baseError = baseErrorResponseEntity.getBody();

        assert baseError != null;

        assertThat(baseError.getErrorCode())
                .isEqualTo(ErrorCode.AUTH_USER_UNAUTHORIZED);

        assertThat(baseError.getMessageAr())
                .isEqualTo("مستخدم غير مصرح به");

        assertThat(baseError.getMessageEn())
                .isEqualTo("Unauthorized User");
    }

}
