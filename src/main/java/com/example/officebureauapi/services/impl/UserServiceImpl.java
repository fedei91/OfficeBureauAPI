package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.entities.User;
import com.example.officebureauapi.repositories.UserRepository;
import com.example.officebureauapi.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);
        userRepository.save(savedUser);


        return savedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User update(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException(String.format("No user found with id %s", id));
        }

        return userRepository.save(existingUser.get());
     }

    @Override
    public User delete(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setDeleted(true);
                    return userRepository.saveAndFlush(user);
                })
                .orElseThrow( () -> new EntityNotFoundException(String.format("No user found with id %s", id)));
    }
}
