package com.nagarro.account.security.util;

import org.springframework.http.HttpHeaders;

import java.util.List;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static final String[] antMatchers = {
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/",
            "/swagger-ui/**",

            "/v1/auth/**"
    };

    public static final List<String> allowedHeaders = List.of(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.SET_COOKIE,
            HttpHeaders.CACHE_CONTROL,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);

}
