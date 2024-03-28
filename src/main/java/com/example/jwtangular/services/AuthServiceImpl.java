package com.example.jwtangular.services;


import com.example.jwtangular.Repository.CustomerRepository;
import com.example.jwtangular.dto.SingupRequest;
import com.example.jwtangular.entites.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AutService {
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean createCustomer(SingupRequest singupRequest) {

        if(customerRepository.existsByEmail(singupRequest.getEmail())){
            return  false;
        }

        Customer customer=new Customer();
        BeanUtils.copyProperties(singupRequest,customer);
        String hashPassword=passwordEncoder.encode(singupRequest.getPassword());
        customer.setPassword(hashPassword);
       customerRepository.save(customer);
        return true;
    }
}
