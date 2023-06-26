package com.nagarro.account.mapper.account.row;

import com.nagarro.account.entity.account.Statement;
import com.nagarro.account.util.DateUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementRowMapper implements RowMapper<Statement> {

    @Override
    public Statement mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Statement
                .builder()
                .id(rs.getLong("ID"))
                .accountId(rs.getLong("account_id"))
                .dateField(DateUtil.convertStringToLocalDate(rs.getString("datefield"), "dd.MM.yyyy"))
                .amount(Double.parseDouble(rs.getString("amount")))
                .build();
    }

}
