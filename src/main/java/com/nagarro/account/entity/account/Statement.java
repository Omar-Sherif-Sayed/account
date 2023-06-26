package com.nagarro.account.entity.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Statement {

    private Long id;
    private Long accountId;
    private LocalDate dateField;
    private double amount;

}
