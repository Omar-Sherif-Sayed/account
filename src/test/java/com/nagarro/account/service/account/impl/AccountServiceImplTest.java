package com.nagarro.account.service.account.impl;

import com.nagarro.account.exception.BaseException;
import com.nagarro.account.model.response.account.AccountResponse;
import com.nagarro.account.model.response.account.StatementResponse;
import com.nagarro.account.repository.account.AccountRepository;
import com.nagarro.account.repository.account.StatementRepository;
import com.nagarro.account.security.util.SecurityApplicationUtil;
import com.nagarro.account.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StatementRepository statementRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isShouldTestSearchForUnauthorizedUser() {

        // given
        Long accountId = 1L;
        SecurityApplicationUtil.setRole("ROLE_USER");

        // then
        String expectedToString = "BaseException(httpStatus=401 UNAUTHORIZED, errorCode=AUTH_USER_UNAUTHORIZED, detail=null)";
        assertThatThrownBy(() -> accountService.search(accountId, null, null, null, null))
                .isInstanceOf(BaseException.class)
                .hasToString(expectedToString);

    }

    @Test
    void isShouldTestSearchForUserForLastThreeMonths() {

        // when
        List<AccountResponse> accountResponses = accountService
                .search(null, null, null, null, null);

        // then
        assertThat(accountResponses)
                .isEmpty();

    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, null, null, null, null, 1, 31",
            "null, '2015-10-14', '2021-10-14', null, null, 5, 10",
            "1, '2015-10-14', '2021-10-14', null, null, 1, 10",
            "1, '2015-10-14', '2021-10-14', 194.1, 500.595, 1, 4",
            "null, '2015-10-14', '2021-10-14', 194.1, 500.595, 5, 4",
            "null, null, null, 194.1, 500.595, 5, 13",
    }, nullValues = {"null"})
    void isShouldTestSearchResults(Long accountId,
                                   LocalDate dateFrom,
                                   LocalDate dateTo,
                                   Double amountFrom,
                                   Double amountTo,
                                   int expectedAccountsSize,
                                   int expectedStatementsSize) {

        SecurityApplicationUtil.setRole("ROLE_ADMIN");

        // when
        List<AccountResponse> accounts = accountService
                .search(accountId, dateFrom, dateTo, amountFrom, amountTo);

        // then
        assertThat(accounts)
                .hasSize(expectedAccountsSize);

        List<StatementResponse> statements = accounts.get(0).getStatements();
        assertThat(statements)
                .hasSize(expectedStatementsSize);

    }

    @Test
    void isShouldTestSearchForSpecificResults() {


        SecurityApplicationUtil.setRole("ROLE_ADMIN");

        // when

        List<AccountResponse> accounts = accountService
                .search(1L,
                        DateUtil.convertStringToLocalDate("2015-10-14", "yyyy-MM-dd"),
                        DateUtil.convertStringToLocalDate("2021-10-14", "yyyy-MM-dd"),
                        194.1,
                        500.595);

        // then

        assertThat(accounts)
                .hasSize(1);

        AccountResponse accountResponse = accounts.get(0);
        assertThat(accountResponse.getId())
                .isEqualTo(1);

        assertThat(accountResponse.getAccountType())
                .isEqualTo("current");

        assertThat(accountResponse.getAccountNumber())
                .isEqualTo("0012250016001");

        StatementResponse statement = accountResponse
                .getStatements()
                .stream()
                .filter(element -> element.getId().equals(9L))
                .findAny()
                .orElse(null);

        assert statement != null;

        assertThat(statement.getId())
                .isEqualTo(9L);

        assertThat(statement.getDateField())
                .isEqualTo(DateUtil.convertStringToLocalDate("2020-10-14", "yyyy-MM-dd"));

        assertThat(statement.getAmount())
                .isEqualTo(196.801905945903);

    }


}
