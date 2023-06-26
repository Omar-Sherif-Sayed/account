package com.nagarro.account.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityApplicationUtil {

    private static String token;
    private static String username;
    private static String role;

    private SecurityApplicationUtil() {
    }

    private static String getJwtSubject() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        var idAndEmail = authentication.getPrincipal();
        return idAndEmail.toString();
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        SecurityApplicationUtil.token = token;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        SecurityApplicationUtil.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        SecurityApplicationUtil.role = role;
    }

}
