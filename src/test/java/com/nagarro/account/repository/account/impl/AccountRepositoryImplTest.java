package com.nagarro.account.repository.account.impl;

import com.nagarro.account.config.DatabaseConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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

        assertThat(accountRepository.findAll())
                .hasSize(5);

    }

}