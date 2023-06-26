package com.nagarro.account.service.auth.impl;

import com.nagarro.account.entity.auth.UserCacheEntity;
import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import com.nagarro.account.model.request.auth.LoginRequest;
import com.nagarro.account.model.response.auth.LoginResponse;
import com.nagarro.account.repository.auth.UserCacheRepository;
import com.nagarro.account.security.auth.ApplicationUser;
import com.nagarro.account.security.config.jwt.JwtConfig;
import com.nagarro.account.security.util.JwtUtil;
import com.nagarro.account.security.util.SecurityApplicationUtil;
import com.nagarro.account.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private final UserCacheRepository userCacheRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest authenticationRequest) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );

        String token = null;
        UserDetails user = null;

        try {
            var authenticate = authenticationManager.authenticate(authentication);
            var applicationUser = (ApplicationUser) authenticate.getPrincipal();
            user = applicationUser.getUser();

            token = jwtUtil.generateJwtToken(authenticate);
        } catch (Exception e) {
            handleLoginException(e);
        }

        if (token == null)
            throw BaseException.builder().errorCode(ErrorCode.AUTH_TOKEN_CAN_NOT_BE_TRUSTED).build();

        userCacheRepository.findById(user.getUsername())
                .ifPresent(element -> {
                    throw BaseException.builder().errorCode(ErrorCode.AUTH_USER_ALREADY_LOGGED_IN).build();
                });

        String username = user.getUsername();
        String role = user.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse(null);
        saveLoginData(username, role, token);

        return LoginResponse
                .builder()
                .token(token)
                .role(role)
                .build();
    }

    private void handleLoginException(Exception e) {
        logger.error("Error in AuthController in login()", e);

        if (e instanceof BadCredentialsException)
            throw BaseException.builder().errorCode(ErrorCode.AUTH_WRONG_CREDENTIALS).build();

        if (e instanceof DisabledException)
            throw BaseException.builder().errorCode(ErrorCode.AUTH_USER_DISABLED).build();

        throw BaseException.builder().errorCode(ErrorCode.AUTH_TOKEN_CAN_NOT_BE_TRUSTED).build();
    }

    private void saveLoginData(String username, String role, String token) {

        UserCacheEntity userCacheEntity = new UserCacheEntity();
        userCacheEntity.setUsername(username);
        userCacheEntity.setToken(token);
        userCacheRepository.save(userCacheEntity);

        SecurityApplicationUtil.setToken(token);
        SecurityApplicationUtil.setUsername(username);
        SecurityApplicationUtil.setRole(role);
    }

    @Override
    public Boolean logout(String authorization) {

        if (authorization != null && authorization.startsWith(jwtConfig.getTokenPrefix())) {
            var token = authorization.split(jwtConfig.getTokenPrefix())[1];

            String username = jwtUtil.getSubjectFromClaims(token);

            if (username != null) {
                Optional<UserCacheEntity> userCacheEntityOptional = userCacheRepository.findById(username);

                if (userCacheEntityOptional.isPresent()) {
                    userCacheRepository.delete(userCacheEntityOptional.get());
                    return true;
                }
            }

        }

        return false;
    }

}
