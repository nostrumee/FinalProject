package com.leverx.tradingview.service.auth.impl;

import com.leverx.tradingview.service.auth.TokenHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenHolderServiceImpl implements TokenHolderService {

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TokenHolderServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String token, String email) {
        redisTemplate.opsForValue().set(token, email);
        redisTemplate.expire(token, 1, TimeUnit.DAYS);
    }

    @Override
    public String retrieveOwner(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }

    @Override
    public boolean isTokenValid(String token) {
        return redisTemplate.hasKey(token);
    }
}
