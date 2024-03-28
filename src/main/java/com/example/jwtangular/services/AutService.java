package com.example.jwtangular.services;

import com.example.jwtangular.dto.SingupRequest;

public interface AutService {
    boolean createCustomer(SingupRequest singupRequest);
}
