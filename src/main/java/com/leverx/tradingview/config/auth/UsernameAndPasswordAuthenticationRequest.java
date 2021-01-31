package com.leverx.tradingview.config.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {

    private String username;

    private String password;

}
