package com.nagarro.account;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@SpringBootTest
class AccountApplicationTests {

    @Test
    void itShouldTestMainMethod() {
        assertThatNoException()
                .isThrownBy(() -> AccountApplication.main(new String[]{}));
    }

}
