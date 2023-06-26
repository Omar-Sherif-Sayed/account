package com.nagarro.account.repository.account.impl;

import com.nagarro.account.entity.account.Statement;
import com.nagarro.account.mapper.account.row.StatementRowMapper;
import com.nagarro.account.repository.account.StatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatementRepositoryImpl implements StatementRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Statement> findAll() {

        String sql = "select ID, account_id, datefield, amount from statement";
        List<Statement> statementList = jdbcTemplate.query(sql, new StatementRowMapper());

        return statementList;
    }

}
