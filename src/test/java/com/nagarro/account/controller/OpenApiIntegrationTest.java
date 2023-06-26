package com.nagarro.account.controller;

import com.nagarro.account.config.OpenApiConfig;
import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import com.nagarro.account.security.config.jwt.JwtConfig;
import com.nagarro.account.security.util.JwtUtil;
import com.nagarro.account.service.account.AccountService;
import com.nagarro.account.service.auth.AuthService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@WebMvcTest
@Import({OpenApiConfig.class,
        ExceptionHandlerUtil.class})
class OpenApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtConfig jwtConfig;


    @Test
    void swaggerJsonExists() throws Exception {
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders.get("/v3/api-docs")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();


        try (Writer writer = new FileWriter(new File("build/swagger.json"))) {
            IOUtils.write(contentAsString, writer);
        }
    }
}
