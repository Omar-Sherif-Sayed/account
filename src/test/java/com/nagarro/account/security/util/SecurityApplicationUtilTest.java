package com.nagarro.account.security.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SecurityApplicationUtilTest {

    @Value("${security.user.password}")
    private String userPassword;

    private final String userUsername = "user";
    private final String userRole = "ROLE_USER";
    private String token;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userUsername, userPassword);
        var authenticate = authenticationManager.authenticate(authentication);
        token = jwtUtil.generateJwtToken(authenticate);

        SecurityApplicationUtil.setToken(token);
        SecurityApplicationUtil.setUsername(userUsername);
        SecurityApplicationUtil.setRole(userRole);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<SecurityApplicationUtil> constructor = SecurityApplicationUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void getToken() {
        assertThat(SecurityApplicationUtil.getToken())
                .startsWith(token);
    }

    @Test
    void getUsername() {
        assertThat(SecurityApplicationUtil.getUsername())
                .isEqualTo(userUsername);
    }

    @Test
    void getRole() {
        assertThat(SecurityApplicationUtil.getRole())
                .isEqualTo(userRole);
    }

}