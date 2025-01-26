package com.ecommerce.backend.Security.Service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.Security.Dto.SignUpRequest;
import com.ecommerce.backend.Security.Dto.UserDto;
import com.ecommerce.backend.Security.Entity.Users;
import com.ecommerce.backend.Security.Enums.UserRole;
import com.ecommerce.backend.Security.Repository.UserRepo;

import jakarta.annotation.PostConstruct;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepo userRepo;

    public UserDto CreateUser(SignUpRequest signUpRequest){

        Users user = new Users();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);

        Users CreateUser = userRepo.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(CreateUser.getId());


        return userDto;
        
    }

    @Override
    public boolean hasUserWithEmail(String email) {
       return userRepo.findFirstByEmail(email).isPresent();
    }


    @PostConstruct
    public void createAdminAccount(){
        Users adminAccount = userRepo.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            Users user = new Users();
            user.setEmail("admin@gamil.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepo.save(user);
        }
    }
}
