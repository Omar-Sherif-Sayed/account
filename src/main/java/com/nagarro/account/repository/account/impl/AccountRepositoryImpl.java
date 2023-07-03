package com.nagarro.account.repository.account.impl;

import com.nagarro.account.entity.account.Account;
import com.nagarro.account.mapper.account.row.AccountRowMapper;
import com.nagarro.account.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Account> findAll() {

        String sql = "select ID, account_type, account_number from account";
        return jdbcTemplate.query(sql, new AccountRowMapper());

    }

}
