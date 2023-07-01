package com.nagarro.account.security.util;

import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import com.nagarro.account.security.config.WebSecurityConfig;
import com.nagarro.account.security.config.jwt.JwtConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ImportAutoConfiguration({
        WebSecurityConfig.class
})
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private ExceptionHandlerUtil exceptionHandlerUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${security.user.password}")
    private String userPassword;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldTestGenerateJwtToken() {

        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", userPassword);
        var authenticate = authenticationManager.authenticate(authentication);

        // when
        String generateJwtToken = jwtUtil.generateJwtToken(authenticate);

        // then
        assertThat(generateJwtToken)
                .startsWith("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiaWF0IjoxNjg4Mj");

    }

    @Test
    void validateJwtToken() {
    }

    @Test
    void itShouldTestParseJwtAndReturningToken() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", userPassword);
        var authenticate = authenticationManager.authenticate(authentication);
        String generateJwtToken = jwtUtil.generateJwtToken(authenticate);

        request.addHeader(HttpHeaders.AUTHORIZATION, jwtConfig.getTokenPrefix() + generateJwtToken);

        // when
        String parseJwt = jwtUtil.parseJwt(request);

        // then
        assertThat(parseJwt)
                .startsWith("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiaWF0IjoxNjg4Mj");
    }

    @Test
    void itShouldTestParseJwtAndReturningNull() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", userPassword);
        var authenticate = authenticationManager.authenticate(authentication);
        String generateJwtToken = jwtUtil.generateJwtToken(authenticate);

        request.addHeader(HttpHeaders.AUTHORIZATION, generateJwtToken);

        // when
        String parseJwt = jwtUtil.parseJwt(request);

        // then
        assertThat(parseJwt)
                .isNull();
    }

    @Test
    void itShouldTestGetSubjectFromClaims() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", userPassword);
        var authenticate = authenticationManager.authenticate(authentication);
        String generateJwtToken = jwtUtil.generateJwtToken(authenticate);

        // when
        String subjectFromClaims = jwtUtil.getSubjectFromClaims(generateJwtToken);

        // then
        assertThat(subjectFromClaims)
                .isEqualTo("user");
    }

}