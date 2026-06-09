package com.beryllinus.backend.factory;

import com.beryllinus.backend.enumuration.AuthProvider;
import com.beryllinus.backend.handler.AuthHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthHandlerFactory {
    private final Map<AuthProvider, AuthHandler> handlerMap;

    public AuthHandlerFactory(List<AuthHandler> handlers) {
        this.handlerMap = handlers.stream().collect(Collectors.toMap(AuthHandler::getProvider, h -> h));
    }

    public AuthHandler getHandler(AuthProvider authProvider) {
        AuthHandler handler = handlerMap.get(authProvider);
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported auth provider: " + authProvider);
        }

        return handler;
    }

}
