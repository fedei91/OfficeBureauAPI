package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.AuthenticationRequestDto;
import com.example.officebureauapi.dto.AuthenticationResponseDto;
import com.example.officebureauapi.dto.RegisterRequestDto;
import com.example.officebureauapi.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
