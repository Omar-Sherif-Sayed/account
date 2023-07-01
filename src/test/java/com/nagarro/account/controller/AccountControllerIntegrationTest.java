package com.nagarro.account.controller;

import com.nagarro.account.config.DatabaseConfig;
import com.nagarro.account.controller.account.AccountController;
import com.nagarro.account.repository.account.AccountRepository;
import com.nagarro.account.repository.account.StatementRepository;
import com.nagarro.account.service.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AccountController.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration-test.yaml")
@ImportAutoConfiguration(DatabaseConfig.class)
public class AccountControllerIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StatementRepository statementRepository;

//    @Test
//    public void shouldSearchFine() throws Exception {
//
//
//        mvc.perform(get("/v1/account/search")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//    }

}
