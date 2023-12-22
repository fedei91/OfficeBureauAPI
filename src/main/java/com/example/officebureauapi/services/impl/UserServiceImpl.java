package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.entities.User;
import com.example.officebureauapi.exceptions.DuplicateEmailException;
import com.example.officebureauapi.mappers.entitytodto.UserEntityToDtoMapper;
import com.example.officebureauapi.repositories.UserRepository;
import com.example.officebureauapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private UserEntityToDtoMapper userEntityToDtoMapper;

    @Override
    public UserDto register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new DuplicateEmailException("Email already registered: " + userDto.getEmail());
        }
        User user = User.builder().build();
        user = userEntityToDtoMapper.toEntity(userDto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.saveAndFlush(user);
        userDto.setId(user.getId().toString());
        return userDto;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        String userId = userDto.getId();
        if (userId == null) {
            throw new IllegalArgumentException("User id must not be null");

        }

        Optional<User> existingUser = userRepository.findById(userId);

        return existingUser.map(user -> {
            User updatedUser = User.builder()
                    .isDeleted(userDto.getIsDeleted())
                    .email(userDto.getEmail())
                    .username(userDto.getUserName())
                    .password(userDto.getPassword())
                    .build();

                    return userEntityToDtoMapper.toDto(userRepository.saveAndFlush(updatedUser));
        })
                .orElseThrow( () -> new EntityNotFoundException(String.format("No user found with id %s", userId)));

     }

    @Override
    public void delete(String id) {
        userRepository.findById(id)
                .map(user -> {
                    user.setDeleted(true);
                    return userRepository.saveAndFlush(user);
                })
                .orElseThrow( () -> new EntityNotFoundException(String.format("No user found with id %s", id)));
    }
}
