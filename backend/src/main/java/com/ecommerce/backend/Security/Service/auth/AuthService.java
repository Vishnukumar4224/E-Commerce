package com.ecommerce.backend.Security.Service.auth;

import com.ecommerce.backend.Security.Dto.SignUpRequest;
import com.ecommerce.backend.Security.Dto.UserDto;

public interface AuthService {

    UserDto CreateUser(SignUpRequest signUpRequest);

    boolean hasUserWithEmail(String email);
}
