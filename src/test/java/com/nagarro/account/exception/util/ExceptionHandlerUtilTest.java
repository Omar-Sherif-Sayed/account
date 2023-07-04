package com.nagarro.account.exception.util;

import com.nagarro.account.enums.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ExceptionHandlerUtilTest {

    @Autowired
    private ExceptionHandlerUtil exceptionHandlerUtil;

    @Autowired
    private MessageSource messageSource;

    @Test
    void itShouldTestReturningEmptyStringWhenGetTranslatedMessage() {
        assertThat(exceptionHandlerUtil.getTranslatedMessage("", Language.AR))
                .isEmpty();
    }

}
