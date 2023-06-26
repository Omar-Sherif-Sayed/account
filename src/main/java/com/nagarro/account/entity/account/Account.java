package com.nagarro.account.entity.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Account {

    private Long id;
    private String accountType;
    private String accountNumber;

}
