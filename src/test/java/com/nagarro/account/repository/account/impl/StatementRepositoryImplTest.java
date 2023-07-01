package com.nagarro.account.repository.account.impl;

import com.nagarro.account.config.DatabaseConfig;
import com.nagarro.account.entity.account.Statement;
import com.nagarro.account.mapper.account.row.AccountRowMapper;
import com.nagarro.account.mapper.account.row.StatementRowMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//        (
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = StatementRepositoryImpl.class)
@ImportAutoConfiguration(DatabaseConfig.class)
class StatementRepositoryImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StatementRepositoryImpl statementRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {

        String sql = "select ID, account_id, datefield, amount from statement";
        assertThat(jdbcTemplate.query(sql, new StatementRowMapper()).size())
//                .isInstanceOf(List < Statement >)
                .isEqualTo(142);

        assertThat(statementRepository.findAll().size())
                .isEqualTo(142);

    }
}