package com.example.springsecurity.dao;


import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface AccountRepostitory extends JpaRepository<Account, Long> {

    public Account findByAccountNumber(String accountNumber);
    public List<Account> findAccountByCustomer(Customer customer);



}
