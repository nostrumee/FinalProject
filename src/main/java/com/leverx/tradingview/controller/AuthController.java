package com.leverx.tradingview.controller;

import com.leverx.tradingview.model.dto.UserCreateDto;
import com.leverx.tradingview.service.UserService;
import com.leverx.tradingview.service.auth.RegistrationService;
import com.leverx.tradingview.service.auth.TokenHolderService;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final TokenHolderService tokenHolderService;
    private final UserService userService;

    public AuthController(RegistrationService registrationService, TokenHolderService tokenHolderService, UserService userService) {
        this.registrationService = registrationService;
        this.tokenHolderService = tokenHolderService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserCreateDto userDto) {
        registrationService.addUser(userDto);
    }

    @PostMapping("/confirm/{token}")
    public String confirmUser(@PathVariable String token) {
        if (StringUtils.isEmpty(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is null or empty");
        }
        try {
            registrationService.confirmToken(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not confirm token");
        }
        return "Token successfully confirmed";
    }

    @GetMapping("/check_code/{token}")
    public String validateToken(@PathVariable String token) {
        return tokenHolderService.isTokenValid(token) ? token : "Token is invalid";
    }

    @PostMapping("/forgot_password")
    public void createAndSendToken(@RequestBody String email) {
        registrationService.createAndSendToken(email, "/reset/");
    }

    @PostMapping("/reset/{token}")
    public String resetPassword(@PathVariable String token, @RequestBody String newPassword) {
        if (StringUtils.isEmpty(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is null or empty");
        }
        try {
            String email = tokenHolderService.retrieveOwner(token);
            userService.updatePassword(email, newPassword);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not reset password");
        }
        return "Password successfully changed";
    }

}