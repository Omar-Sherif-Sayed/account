package com.nagarro.account.exception;

import com.nagarro.account.exception.enums.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Builder.Default
    private HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    private ErrorCode errorCode;
    private Object detail;

}
