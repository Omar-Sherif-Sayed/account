package com.nagarro.account.model.response.account;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StatementResponse {

    private Long id;
    private LocalDate dateField;
    private double amount;

}
