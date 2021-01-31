package com.leverx.tradingview.service;

import com.leverx.tradingview.model.dto.UserCreateDto;
import com.leverx.tradingview.model.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getById(Integer id);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUser(Integer id, UserDto user);

    UserDto deleteUser(Integer id);

    UserDto updatePassword(String email, String password);

}
