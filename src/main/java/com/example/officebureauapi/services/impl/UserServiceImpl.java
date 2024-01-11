package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.entities.User;
import com.example.officebureauapi.exceptions.DuplicateEmailException;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.mappers.entitytodto.UserEntityToDtoMapper;
import com.example.officebureauapi.repositories.UserRepository;
import com.example.officebureauapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserEntityToDtoMapper userEntityToDtoMapper;

    @Override
    public UserDto register(UserDto userDto) {
        verifyUserEmail(userDto);

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
    public UserDto update(String userId, UserDto updatedUserDto) {
        verifyUserEmail(updatedUserDto);

        User existingUser = findById(userId);

        existingUser = userEntityToDtoMapper.toEntity(updatedUser, existingUser);
        userRepository.save(existingUser);

        return userEntityToDtoMapper.toDto(existingUser);

     }

    @Override
    public void delete(String id) {
        User existingUser = findById(id);

        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }

    @Override
    public UserDto findUserById(String id) {
        UUID userId;
        try {
            userId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdException("Invalid user id format", ex);
        }

        Optional<User> existingUser = userRepository.findById(userId);

        return existingUser.map(userEntityToDtoMapper::toDto)
                .orElseThrow( () -> new EntityNotFoundException(String.format("No user found with id %s", id)));
    }

    @Override
    public User findById(String userId) {
        UUID id = UUID.fromString(userId);

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found", userId)));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> userMapper.toDto(user, UserDto.builder().build()));
    }

    private void verifyUserEmail(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already registered: " + userDto.getEmail());
        }
    }
}
