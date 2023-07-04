package com.nagarro.account.controller.account;

import com.nagarro.account.model.response.BaseResponse;
import com.nagarro.account.model.response.account.AccountResponse;
import com.nagarro.account.model.response.account.StatementResponse;
import com.nagarro.account.service.account.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldTestSuccessfulSearch() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        LocalDate now = LocalDate.now();

        List<AccountResponse> accountResponses = new ArrayList<>();
        List<StatementResponse> statements = new ArrayList<>();
        StatementResponse statementResponse = StatementResponse
                .builder()
                .id(1L)
                .amount(15.1)
                .dateField(now)
                .build();
        statements.add(statementResponse);
        AccountResponse current = AccountResponse
                .builder()
                .id(1L)
                .accountType("current")
                .accountNumber("123")
                .statements(statements)
                .build();
        accountResponses.add(current);

        when(accountService.search(any(Long.class), any(LocalDate.class), any(LocalDate.class), any(Double.class),
                any(Double.class)))
                .thenReturn(accountResponses);

        ResponseEntity<BaseResponse<List<AccountResponse>>> responseEntity = accountController
                .search(1L, now, now, 454.1, 1455.41);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<AccountResponse> accountResponseList = Objects.requireNonNull(responseEntity.getBody()).getResponse();
        Assertions.assertThat(accountResponseList)
                .hasSize(1);

        Assertions.assertThat(accountResponseList.get(0).getId())
                .isEqualTo(1L);

        Assertions.assertThat(accountResponseList.get(0).getAccountType())
                .isEqualTo("current");

        Assertions.assertThat(accountResponseList.get(0).getAccountNumber())
                .isEqualTo("123");

        List<StatementResponse> statementsResponseList = accountResponseList.get(0).getStatements();

        Assertions.assertThat(statementsResponseList.get(0).getId())
                .isEqualTo(1L);

        Assertions.assertThat(statementsResponseList.get(0).getAmount())
                .isEqualTo(15.1);

        Assertions.assertThat(statementsResponseList.get(0).getDateField())
                .isEqualTo(now);
    }

}
