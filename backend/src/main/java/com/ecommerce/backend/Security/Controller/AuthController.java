package com.ecommerce.backend.Security.Controller;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.backend.Security.Dto.AuthenticationRequest;
import com.ecommerce.backend.Security.Dto.SignUpRequest;
import com.ecommerce.backend.Security.Dto.UserDto;
import com.ecommerce.backend.Security.Entity.Users;
import com.ecommerce.backend.Security.Repository.UserRepo;
import com.ecommerce.backend.Security.Service.auth.AuthService;
import com.ecommerce.backend.Security.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

    private final String TOKEN_PREFIX = "Bearer ";

    private final String HEADER_STRING = "Authorizataion";
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepo userRepo;

    private final JwtUtil jwtUtil;

    private final AuthService authService;


    @PostMapping("/authenticate")
    public void CreateAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                            HttpServletResponse response) throws IOException, JSONException{
    
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password.");
        }

        final UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<Users> optionaluser = userRepo.findFirstByEmail(userdetails.getUsername());

        final String jwt = jwtUtil.generateToken(userdetails.getUsername());

        if(optionaluser.isPresent()){
            response.getWriter().write(new JSONObject()
            .put("userId", optionaluser.get().getName())
            .put("role", optionaluser.get().getRole())
            .toString());

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }



    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest){
        if (authService.hasUserWithEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>("User Already Exists", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.CreateUser(signUpRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
