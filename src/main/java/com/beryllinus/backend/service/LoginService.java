package com.beryllinus.backend.service;

import com.beryllinus.backend.configuration.AuthProperties;
import com.beryllinus.backend.dto.LoginRequestDTO;
import com.beryllinus.backend.enumuration.AuthProvider;
import com.beryllinus.backend.factory.AuthHandlerFactory;
import com.beryllinus.backend.handler.AuthHandler;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private AuthHandlerFactory authHandlerFactory;
    private AuthProperties authProperties;

    public void authenticate(LoginRequestDTO loginRequestDTO) {

       AuthHandler handler = authHandlerFactory.getHandler(AuthProvider.valueOf(authProperties.getProvider().toUpperCase()));
       handler.login(loginRequestDTO);

    }
}
