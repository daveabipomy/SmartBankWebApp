package com.example.springsecurity.dao;


import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    public List<Transaction> findTransactionByAccount(Account account);
}
