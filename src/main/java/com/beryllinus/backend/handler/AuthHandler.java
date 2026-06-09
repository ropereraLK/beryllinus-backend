package com.beryllinus.backend.handler;

import com.beryllinus.backend.dto.LoginRequestDTO;
import com.beryllinus.backend.dto.LoginResponseDTO;
import com.beryllinus.backend.enumuration.AuthProvider;

public interface AuthHandler {
    AuthProvider getProvider();
    LoginResponseDTO login(LoginRequestDTO request);
}