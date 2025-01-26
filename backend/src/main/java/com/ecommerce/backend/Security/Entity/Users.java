package com.ecommerce.backend.Security.Entity;

import com.ecommerce.backend.Security.Enums.UserRole;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;
    
    private UserRole role;


    @Lob
    private byte[] img;

}
