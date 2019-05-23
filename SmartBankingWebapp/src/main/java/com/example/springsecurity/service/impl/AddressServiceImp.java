package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.AddressRepository;
import com.example.springsecurity.model.Address;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(long id) {
        return addressRepository.findById(id).orElse(null);
    }
}
