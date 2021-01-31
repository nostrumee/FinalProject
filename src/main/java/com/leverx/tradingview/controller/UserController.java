package com.leverx.tradingview.controller;

import com.leverx.tradingview.model.dto.UserCreateDto;
import com.leverx.tradingview.model.dto.UserDto;
import com.leverx.tradingview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto user) throws URISyntaxException {
        UserDto userDto = userService.createUser(user);
        return ResponseEntity.created(getUserUri(userDto.getId())).body(userDto);
    }

    private URI getUserUri(Integer id) throws URISyntaxException {
        return new URI(String.format("/users/%d", id));
    }

    @DeleteMapping("/users/{id}")
    public UserDto deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    public UserDto updateComment(@PathVariable Integer id, @RequestBody UserDto user) {
        return userService.updateUser(id, user);
    }

}
