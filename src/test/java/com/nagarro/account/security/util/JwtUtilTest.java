package com.nagarro.account.security.util;

import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import com.nagarro.account.security.config.WebSecurityConfig;
import com.nagarro.account.security.config.jwt.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.io.IOException;

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
                .startsWith("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV");

    }

    @Test
    void validateJwtTokenWithExpiredToken() throws IOException {

        // given
        MockHttpServletResponse response = new MockHttpServletResponse();
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2ODgyMjI2NzIsImV4cCI6MTY4ODIyMjk3Mn0.RuzgixQzRfXwC9T4Hg-nRJs2ZFb8CuArCwI44eemy_k";

        // when
        Jws<Claims> claimsJws = jwtUtil.validateJwtToken(jwtToken, response);

        // then
        assertThat(claimsJws)
                .isNull();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());

        assertThat(response.getContentAsString())
                .contains("errorCode\":\"AUTH_TOKEN_EXPIRED\"");

    }

    @ParameterizedTest
    @CsvSource({
            "'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.Nb76oh-guwaLr6_2P5bYb-Bv0tIQuwa_6FYiKXyZwsM', 'errorCode\":\"AUTH_TOKEN_SIGNATURE\"'",
            "'invalid_grant:Invalid', 'errorCode\":\"AUTH_TOKEN_INVALID\"'",
            "'', 'errorCode\":\"AUTH_TOKEN_HAS_EMPTY_CLAIMS\"'",
    })
    @DisplayName("It should test invalid jwt token")
    void itShouldTestValidateJwtTokenWithInvalidJwtSignatureOrInvalidJwtTokenOrEmptyToken(String jwtToken,
                                                                                          String expected) throws IOException {
        // given
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        Jws<Claims> claimsJws = jwtUtil.validateJwtToken(jwtToken, response);

        // then
        assertThat(claimsJws)
                .isNull();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());

        assertThat(response.getContentAsString())
                .contains(expected);

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
                .startsWith("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXV");
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


    @Test
    void itShouldTestGetSubjectFromClaimsWithExpiredToken() {
        // given
        String generateJwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2ODgyMjI2NzIsImV4cCI6MTY4ODIyMjk3Mn0.RuzgixQzRfXwC9T4Hg-nRJs2ZFb8CuArCwI44eemy_k";

        // when
        String subjectFromClaims = jwtUtil.getSubjectFromClaims(generateJwtToken);

        // then
        assertThat(subjectFromClaims)
                .isEqualTo("admin");
    }

}