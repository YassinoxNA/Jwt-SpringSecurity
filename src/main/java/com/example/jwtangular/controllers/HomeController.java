package com.example.jwtangular.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api")
public class HomeController {

    @GetMapping("/hello")
    public String hello(){
        return  "Hello From ";
    }
}
