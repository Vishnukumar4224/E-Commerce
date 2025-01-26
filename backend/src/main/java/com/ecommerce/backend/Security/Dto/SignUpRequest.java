package com.ecommerce.backend.Security.Dto;

import lombok.Data;

@Data

public class SignUpRequest {

    private String email;

    private String password;

    private String name;

}
