package com.example.jwtangular.controllers;

import com.example.jwtangular.dto.SingupRequest;
import com.example.jwtangular.services.AutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SingupController {
    private final AutService autService;

    @Autowired
    public SingupController(AutService autService) {
        this.autService = autService;
    }
    @PostMapping
    public ResponseEntity<?> singupCustomer(@RequestBody SingupRequest singupRequest){
        boolean isUserCreated=autService.createCustomer(singupRequest);
        if(isUserCreated ){
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer created Succeffully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" " +
                    "Failed To Create");
        }

    }
}
