package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.entities.User;

import java.util.List;

public interface UserService {
    UserDto register(UserDto userDto);
    void save(User user);
    UserDto update(UserDto userDto);
    void delete(String id);
    UserDto findUserById(String id);
    List<UserDto> findAll();
}
