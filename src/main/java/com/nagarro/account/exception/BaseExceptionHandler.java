package com.nagarro.account.exception;

import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class BaseExceptionHandler {

    private final ExceptionHandlerUtil exceptionHandlerUtil;

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<BaseError> baseExceptionHandler(BaseException baseException) {
        return exceptionHandlerUtil
                .commonReturn(BaseException
                        .builder()
                        .errorCode(baseException.getErrorCode())
                        .httpStatus(baseException.getHttpStatus())
                        .detail(baseException.getDetail())
                        .build());
    }

}
