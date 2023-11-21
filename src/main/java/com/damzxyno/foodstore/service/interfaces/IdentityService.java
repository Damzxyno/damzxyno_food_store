package com.damzxyno.foodstore.service.interfaces;

import com.damzxyno.foodstore.dto.request.identity.LoginRequest;
import com.damzxyno.foodstore.dto.response.identity.LoginResponse;

public interface IdentityService {
    LoginResponse Login(LoginRequest request);
}
