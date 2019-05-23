package com.example.springsecurity.dao;


import com.example.springsecurity.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springsecurity.model.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public Customer findCustomerByLogin(Login login);
}
