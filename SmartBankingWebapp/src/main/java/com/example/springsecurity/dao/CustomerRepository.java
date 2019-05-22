package com.example.springsecurity.dao;


import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    public Customer findCustomerByLogin(Login login);
}
