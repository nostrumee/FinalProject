package com.leverx.tradingview.service.impl;

import com.leverx.tradingview.model.dto.UserCreateDto;
import com.leverx.tradingview.model.jpa.User;
import com.leverx.tradingview.model.dto.UserDto;
import com.leverx.tradingview.repository.UserRepository;
import com.leverx.tradingview.service.UserService;
import com.leverx.tradingview.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    //private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        LOGGER.info("Retrieving all users");
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Integer id) {
        LOGGER.info("Retrieving user by id {}", id);
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        LOGGER.info("Creating user");
        User user = new User();
        user.setPassword(userCreateDto.getPassword());
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(Integer id, UserDto userDto) {
        LOGGER.info("Updating user by id {}", id);
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userDto.setId(id);
        userRepository.save(mapper.map(userDto, User.class));
        return userDto;
    }

    @Override
    public UserDto deleteUser(Integer id) {
        LOGGER.info("Deleting user by id {}", id);
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updatePassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setPassword(password);
        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }


}
