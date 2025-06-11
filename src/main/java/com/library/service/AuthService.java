package com.library.service;

import com.library.dto.LoginRequest;
import com.library.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public LoginResponse authenticate(LoginRequest request) {
        if ("admin@library.com".equals(request.email()) && "admin123".equals(request.password())) {
            return new LoginResponse("token-jwt-exemplo", 1L, request.email());
        }
        throw new RuntimeException("Credenciais inválidas");
    }
}