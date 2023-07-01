package com.nagarro.account.exception;

import com.nagarro.account.exception.enums.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serial;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1582167443169968009L;

    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    private ErrorCode errorCode;
    private Object detail;

}
