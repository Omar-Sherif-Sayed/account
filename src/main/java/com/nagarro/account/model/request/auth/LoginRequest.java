package com.nagarro.account.model.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -1190699731802938976L;

    @JsonProperty(value="username", required = true)
    @NotNull(message = "Please provide a valid username")
    private String username;

    @JsonProperty(value="password", required = true)
    @NotNull(message = "Please provide a valid password")
    private String password;

}
