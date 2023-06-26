package com.nagarro.account.controller.auth;

import com.nagarro.account.model.request.auth.LoginRequest;
import com.nagarro.account.model.response.BaseResponse;
import com.nagarro.account.model.response.auth.LoginResponse;
import com.nagarro.account.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest authenticationRequest) {
        return ResponseEntity.ok(new BaseResponse<>(authService.login(authenticationRequest)));
    }

    @DeleteMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Boolean>> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        return ResponseEntity.ok(new BaseResponse<>(authService.logout(authorization)));
    }

}
