package com.example.springsecurity.service;

import com.example.springsecurity.model.Customer;

import java.util.List;

public interface CustomerService {

    public List<Customer> findAll();
    public Customer save(Customer customer);
    public Customer findById(long id);
    public List<Customer> findAllNotApproved();
    public List<Customer> findAllApproved();
    public List<Customer> findAllActiveCustomer();

}
