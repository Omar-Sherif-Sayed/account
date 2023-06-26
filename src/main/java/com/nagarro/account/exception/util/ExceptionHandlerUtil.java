package com.nagarro.account.exception.util;

import com.nagarro.account.enums.Language;
import com.nagarro.account.exception.BaseError;
import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Log4j2
@Component
@RequiredArgsConstructor
public class ExceptionHandlerUtil {

    private final MessageSource messageSource;

    public ResponseEntity<BaseError> commonReturn(BaseException e) {
        ErrorCode errorCode = e.getErrorCode();
        BaseError errorInfo = new BaseError(errorCode,
                getTranslatedMessage(errorCode.getMessageCode(), Language.AR),
                getTranslatedMessage(errorCode.getMessageCode(), Language.EN),
                e.getDetail());

        log.warn("base exception: errorInfo={}, description={}", errorInfo, errorCode.getDescription());
        return new ResponseEntity<>(errorInfo, e.getHttpStatus());
    }

    public String getTranslatedMessage(String message, Language language) {
        return !Strings.isEmpty(message)
                ? messageSource.getMessage(message, null, new Locale(language.getCode()))
                : "";
    }

}
