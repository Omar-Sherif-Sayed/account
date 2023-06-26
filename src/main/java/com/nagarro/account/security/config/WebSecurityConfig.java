package com.nagarro.account.security.config;


import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import com.nagarro.account.security.auth.ApplicationUserService;
import com.nagarro.account.security.exception.CustomAccessDeniedHandler;
import com.nagarro.account.security.exception.CustomAuthenticationFailureHandler;
import com.nagarro.account.security.filter.JwtAuthenticationFilter;
import com.nagarro.account.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final ExceptionHandlerUtil exceptionHandlerUtil;
    private final ApplicationUserService applicationUserService;
    private final AuthenticationConfiguration authConfiguration;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .securityMatchers(matchers -> matchers
//                        .requestMatchers("/v1/auth/**")
//                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SecurityUtil.antMatchers).permitAll()
                        .requestMatchers("/v1/search").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )

                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(exceptionHandlerUtil);
    }

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("DELETE");
        config.setAllowedMethods(List.of("*", "GET", "POST", "PUT", "PUT", "OPTIONS", "PATCH", "DELETE"));
        config.setAllowedHeaders(SecurityUtil.allowedHeaders);
        config.setExposedHeaders(SecurityUtil.allowedHeaders);
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedOriginPatterns(List.of("*"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
