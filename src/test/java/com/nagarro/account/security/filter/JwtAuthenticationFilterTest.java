package com.nagarro.account.security.filter;

import com.nagarro.account.security.config.jwt.JwtConfig;
import com.nagarro.account.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

@SpringBootTest
class JwtAuthenticationFilterTest {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${security.user.password}")
    private String userPassword;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldTestDoFilterInternalWithoutJwt() throws ServletException, IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        assertThatNoException()
                .isThrownBy(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
    }

    @Test
    void itShouldTestDoFilterInternalWithJwt() throws ServletException, IOException {

        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = new MockFilterChain();

        Authentication authentication = new UsernamePasswordAuthenticationToken("user", userPassword);
        request.setUserPrincipal(authentication);

        var authenticate = authenticationManager.authenticate(authentication);
        String generateJwtToken = jwtUtil.generateJwtToken(authenticate);
        request.addHeader(HttpHeaders.AUTHORIZATION, jwtConfig.getTokenPrefix() + generateJwtToken);


        // then
        assertThatNoException()
                .isThrownBy(() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));

    }

}