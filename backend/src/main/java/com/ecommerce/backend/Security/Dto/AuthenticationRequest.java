package com.ecommerce.backend.Security.Dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;

    private String password;

}
