package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.UserDto;
import com.example.officebureauapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable String id
    ) {
        UserDto existingUser = userService.findUserById(id);
        return ResponseEntity.ok(existingUser);
    }
}
