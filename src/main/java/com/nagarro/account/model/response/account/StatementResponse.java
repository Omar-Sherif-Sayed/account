package com.nagarro.account.model.response.account;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StatementResponse {

    private Long id;
    private LocalDate dateField;
    private double amount;

}
