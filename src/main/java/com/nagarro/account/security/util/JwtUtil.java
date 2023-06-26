package com.nagarro.account.security.util;

import com.google.gson.Gson;
import com.nagarro.account.exception.BaseError;
import com.nagarro.account.exception.BaseException;
import com.nagarro.account.exception.enums.ErrorCode;
import com.nagarro.account.exception.util.ExceptionHandlerUtil;
import com.nagarro.account.security.auth.ApplicationUser;
import com.nagarro.account.security.config.jwt.JwtConfig;
import com.nagarro.account.util.DateUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Logger logger = LogManager.getLogger(JwtUtil.class);

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final ExceptionHandlerUtil exceptionHandlerUtil;

    public String generateJwtToken(Authentication authentication) {

        var applicationUser = (ApplicationUser) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String subject = applicationUser.getUser().getUsername();

        return generateJwt(subject, authorities);
    }

    public String generateJwt(String subject, Collection<? extends GrantedAuthority> authorities) {

        var now = DateUtil.getCairoZonedLocalDateTime();
        var expirationDateSeconds = now.plusMinutes(jwtConfig.getTokenExpirationAfterMinutes());
//
//        String role = authorities.stream().findFirst().map(GrantedAuthority::getAuthority).orElse(null);
//
//        assert role != null;

        return Jwts.builder()
                .setSubject(subject)
                .claim("authorities", authorities)
                .setIssuedAt(DateUtil.convertLocalDateTimeToCairoZone(now))
                .setExpiration(java.sql.Timestamp.valueOf(expirationDateSeconds))
                .signWith(secretKey)
                .compact();
    }

    public Jws<Claims> validateJwtToken(String jwt,
                                        HttpServletResponse response) throws IOException {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt);

        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {0}", e);
            returnException(response, ErrorCode.AUTH_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {0}", e);
            returnException(response, ErrorCode.AUTH_TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {0}", e);
            returnException(response, ErrorCode.AUTH_TOKEN_INVALID);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {0}", e);
            returnException(response, ErrorCode.AUTH_TOKEN_SIGNATURE);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {0}", e);
            returnException(response, ErrorCode.AUTH_TOKEN_HAS_EMPTY_CLAIMS);
        } catch (Exception e) {
            returnException(response, ErrorCode.AUTH_TOKEN_CAN_NOT_BE_TRUSTED);
        }
        return null;
    }

    private void returnException(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        var encoding = "UTF-8";
        response.setContentType("application/json; charset=" + encoding);
        response.setCharacterEncoding(encoding);
        response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        ResponseEntity<BaseError> baseErrorResponseEntity = exceptionHandlerUtil
                .commonReturn(BaseException
                        .builder()
                        .errorCode(errorCode)
                        .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                        .build());
        String toJson = new Gson().toJson(baseErrorResponseEntity.getBody());
        response.getWriter().println(toJson);
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(jwtConfig.getTokenPrefix())) {
            int length = headerAuth.length();
            return headerAuth.substring(7, length);
        }

        return null;
    }

    public String getSubjectFromClaims(String jwt) {
        try {
            var claims = Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            logger.info("JWT token is expired in getSubjectFromClaims: ", e);
            var claims = e.getClaims();
            return claims.getSubject();
        }
    }

}
