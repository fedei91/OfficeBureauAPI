package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.AuthenticationRequestDto;
import com.example.officebureauapi.dto.AuthenticationResponseDto;
import com.example.officebureauapi.dto.RegisterRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseDto register(RegisterRequestDto request);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
