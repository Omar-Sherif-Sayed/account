package com.nagarro.account.mapper.account;

import com.nagarro.account.entity.account.Account;
import com.nagarro.account.entity.account.Statement;
import com.nagarro.account.model.response.account.AccountResponse;

import java.util.List;

public class AccountResponseMapper {

    private AccountResponseMapper() {
    }

    public static AccountResponse toResponse(Account account, List<Statement> statements) {
        return AccountResponse
                .builder()
                .id(account.getId())
                .accountType(account.getAccountType())
                .accountNumber(account.getAccountNumber())
                .statements(statements.stream().map(StatementResponseMapper::toResponse).toList())
                .build();
    }

}
