package com.ecommerce.backend.Security.Service.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.backend.Security.Entity.Users;
import com.ecommerce.backend.Security.Repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionaluser = userRepo.findFirstByEmail(username); 

        if(optionaluser.isEmpty()) throw new UsernameNotFoundException("User Not Found", null);

        return new org.springframework.security.core.userdetails.User(optionaluser.get().getEmail(),optionaluser.get().getPassword()
        , new ArrayList<>());

        
    }

}
