package com.beryllinus.backend.handler;

import com.beryllinus.backend.dto.LoginRequestDTO;
import com.beryllinus.backend.dto.LoginResponseDTO;
import com.beryllinus.backend.enumuration.AuthProvider;
import org.springframework.stereotype.Component;

@Component//("AUTH0")
public class AuthHandlerAuth0Impl implements AuthHandler{
    @Override
    public AuthProvider getProvider() {
        return AuthProvider.AUTH0;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        //Set email, password
        //Get Credentials

        return null;
    }
}
