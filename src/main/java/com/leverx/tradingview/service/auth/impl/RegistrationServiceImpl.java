package com.leverx.tradingview.service.auth.impl;

import com.leverx.tradingview.model.dto.UserCreateDto;
import com.leverx.tradingview.model.jpa.User;
import com.leverx.tradingview.repository.UserRepository;
import com.leverx.tradingview.service.EmailService;
import com.leverx.tradingview.service.auth.RegistrationService;
import com.leverx.tradingview.service.auth.TokenHolderService;
import com.leverx.tradingview.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final TokenHolderService tokenHolderService;
    private final ModelMapper mapper;
    private final EmailService emailService;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, TokenHolderService tokenHolderService, ModelMapper mapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenHolderService = tokenHolderService;
        this.mapper = mapper;
        this.emailService = emailService;
    }

    @Override
    public void addUser(UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);
        userRepository.save(user);
        createAndSendToken(userDto.getEmail(), "/auth/confirm/");
    }

    @Override
    public void createAndSendToken(String email, String link) {
        String token = String.valueOf(generateHash(email, new Date()));
        tokenHolderService.save(token, email);
        emailService.sendEmail(email, "Complete verification", link + token);
    }

    private int generateHash(Object... values) {
        return Math.abs(Objects.hash(values));
    }

    @Override
    public void confirmToken(String token) {
        if (tokenHolderService.isTokenValid(token)) {
            String email = tokenHolderService.retrieveOwner(token);
            User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
            user.setConfirmed(true);
            userRepository.save(user);
        }
    }
}
