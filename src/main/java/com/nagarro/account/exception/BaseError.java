package com.nagarro.account.exception;

import com.nagarro.account.exception.enums.ErrorCode;
import lombok.Data;
import org.apache.commons.lang3.time.FastDateFormat;

@Data
public class BaseError {

    private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    private final boolean status;
    private final String timestamp;
    private final ErrorCode errorCode;
    private final String messageAr;
    private final String messageEn;
    private final Object detail;

    public BaseError(ErrorCode errorCode, String messageAr, String messageEn, Object detail) {
        this.status = false;
        this.timestamp = DATE_FORMAT.format(System.currentTimeMillis());
        this.errorCode = errorCode;
        this.messageAr = messageAr;
        this.messageEn = messageEn;
        this.detail = detail;
    }

}
