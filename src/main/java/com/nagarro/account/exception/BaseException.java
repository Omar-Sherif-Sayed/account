package com.nagarro.account.exception;

import com.nagarro.account.exception.enums.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serial;


@Getter
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1582167443169968009L;

    @Builder.Default
    private final HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    private final ErrorCode errorCode;
    private final transient Object detail;

}
