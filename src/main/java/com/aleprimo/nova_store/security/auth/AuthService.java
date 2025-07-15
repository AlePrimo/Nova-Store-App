package com.aleprimo.nova_store.security.auth;

import com.aleprimo.nova_store.models.UserEntity;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    UserEntity register(RegisterRequest request);
}
