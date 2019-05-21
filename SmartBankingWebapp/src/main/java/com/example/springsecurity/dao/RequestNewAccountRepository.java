package com.example.springsecurity.dao;


import com.example.springsecurity.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestNewAccountRepository extends CrudRepository<Customer,Long> {
}
