package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDto register(UserDto userDto);
    void save(User user);
    UserDto update(String id, UserDto dto);
    void delete(String id);
    User findById(String id);
    UserDto findUserById(String id);
    Page<UserDto> findAll(Pageable pageable);
}
