package com.example.jwtangular.controllers;


import com.example.jwtangular.dto.LoginRequest;
import com.example.jwtangular.dto.LoginResponse;
import com.example.jwtangular.services.jwt.CustomerServiceImpl;
import com.example.jwtangular.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final   AuthenticationManager authenticationManager;
    private final  CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           CustomerServiceImpl customerService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),loginRequest.getPassword()));

        }catch (AuthenticationException e){
            e.printStackTrace(); // Add this line or use a logger to log the exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails;
        try {
           userDetails=customerService.loadUserByUsername(loginRequest.getEmail());

        }
        catch (AuthenticationException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
        String jwt=jwtUtil.generateToken(userDetails.getUsername());
        System.out.println("JWT Token: " + jwt);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LoginResponse(jwt) );

    }
}
