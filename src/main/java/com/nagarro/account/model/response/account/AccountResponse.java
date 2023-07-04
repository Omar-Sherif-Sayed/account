package com.nagarro.account.model.response.account;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountResponse {

    private Long id;
    private String accountType;
    private String accountNumber;
    private List<StatementResponse> statements;

}
