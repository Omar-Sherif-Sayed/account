package com.nagarro.account.mapper.account;

import com.nagarro.account.entity.account.Statement;
import com.nagarro.account.model.response.account.StatementResponse;

public class StatementResponseMapper {

    private StatementResponseMapper() {
    }

    public static StatementResponse toResponse(Statement statement) {
        return StatementResponse
                .builder()
                .id(statement.getId())
                .dateField(statement.getDateField())
                .amount(statement.getAmount())
                .build();
    }

}
