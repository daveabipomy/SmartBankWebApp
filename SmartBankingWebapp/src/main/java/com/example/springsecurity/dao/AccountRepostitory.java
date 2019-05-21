package com.example.springsecurity.dao;


import com.example.springsecurity.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface AccountRepostitory extends CrudRepository<Account, Long> {

    public Account findByAccountNumber(String accountNumber);


}
