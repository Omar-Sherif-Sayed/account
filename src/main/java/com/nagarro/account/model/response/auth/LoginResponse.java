package com.nagarro.account.model.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String token;
    private String role;

}
