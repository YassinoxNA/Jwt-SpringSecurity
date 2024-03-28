package com.example.jwtangular.services.jwt;

import com.example.jwtangular.Repository.CustomerRepository;
import com.example.jwtangular.entites.Customer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerServiceImpl implements UserDetailsService {
    private CustomerRepository customerRepository;


    public CustomerServiceImpl(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found by Email: " + email));

        return new User(customer.getEmail(), customer.getPassword(), Collections.emptyList());
    }
}
