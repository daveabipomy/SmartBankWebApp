package com.example.springsecurity.service;

import com.example.springsecurity.model.Address;

import java.util.List;

public interface AddressService {
    public List<Address> findAll();
    public Address findById(long id);
}
