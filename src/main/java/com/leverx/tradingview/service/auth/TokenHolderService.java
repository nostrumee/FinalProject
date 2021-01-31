package com.leverx.tradingview.service.auth;

public interface TokenHolderService {

    void save(String token, String email);

    String retrieveOwner(String token);

    boolean isTokenValid(String token);

}
