package com.nagarro.account.service.auth;

import com.nagarro.account.model.request.auth.LoginRequest;
import com.nagarro.account.model.response.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest authenticationRequest);

    Boolean logout(String authorization);

}
