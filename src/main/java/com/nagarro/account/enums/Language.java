package com.nagarro.account.enums;

import lombok.Getter;

@Getter
public enum Language {

    AR("ar"),
    EN("en");

    private final String code;

    Language(String code) {
        this.code = code;
    }

}
