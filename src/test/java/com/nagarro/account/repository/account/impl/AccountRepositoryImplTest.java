package com.nagarro.account.repository.account.impl;

import com.nagarro.account.config.DatabaseConfig;
import com.nagarro.account.controller.account.AccountController;
import com.nagarro.account.mapper.account.row.AccountRowMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@SpringBootTest //(
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = AccountRepositoryImpl.class)
@ImportAutoConfiguration(DatabaseConfig.class)
class AccountRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepositoryImpl accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepositoryImpl(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {

        String sql = "select ID, account_type, account_number from account";
        assertThat(jdbcTemplate.query(sql, new AccountRowMapper()).size())
                .isEqualTo(5);

        assertThat(accountRepository.findAll().size())
                .isEqualTo(5);

    }

}