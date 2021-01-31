package com.leverx.tradingview.service.auth;

import com.leverx.tradingview.model.dto.UserCreateDto;

public interface RegistrationService {

    void addUser(UserCreateDto user);

    void confirmToken(String token);

    void createAndSendToken(String email, String link);

}
