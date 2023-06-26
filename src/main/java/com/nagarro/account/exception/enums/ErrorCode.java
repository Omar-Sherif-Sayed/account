package com.nagarro.account.exception.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    AUTH_USER_UNAUTHORIZED("auth.user.unauthorized", "Unauthorized User"),
    AUTH_WRONG_USER_NAME("auth.wrong.user.name", "Wrong User name"),
    AUTH_WRONG_USER_PASSWORD("auth.wrong.user.password", "Wrong User password"),
    AUTH_WRONG_CREDENTIALS("auth.wrong.credentials", "Wrong User credentials"),
    AUTH_USER_DISABLED("auth.user.disabled", "User disabled"),
    AUTH_TOKEN_EXPIRED("auth.token.expired", "Token expired"),
    AUTH_NOT_FOUND_USER("auth.not.found.user", "Not found user"),
    AUTH_TOKEN_CAN_NOT_BE_TRUSTED("auth.token.can.not.be.trusted", "Token cannot be trusted"),
    AUTH_TOKEN_UNSUPPORTED("auth.token.unsupported", "Token is expired"),
    AUTH_TOKEN_INVALID("auth.token.invalid", "Token is invalid"),
    AUTH_TOKEN_SIGNATURE("auth.token.invalid.signature", "Token has invalid signature"),
    AUTH_TOKEN_HAS_EMPTY_CLAIMS("auth.token.has.empty.claims", "Token has empty claims"),
    AUTH_USER_ALREADY_LOGGED_IN("auth.user.already.logged.id", "User already logged in"),
    BAD_DATE_FORMATTER("bad.date.formatter", "Bad Date Formatter");

    private final String messageCode;
    private final String description;

    ErrorCode(String messageCode, String description) {
        this.messageCode = messageCode;
        this.description = description;
    }

}
