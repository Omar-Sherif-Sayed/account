package com.nagarro.account.mapper.account.row;

import com.nagarro.account.entity.account.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Account
                .builder()
                .id(rs.getLong("ID"))
                .accountType(rs.getString("account_type"))
                .accountNumber(rs.getString("account_number"))
                .build();
    }

}
