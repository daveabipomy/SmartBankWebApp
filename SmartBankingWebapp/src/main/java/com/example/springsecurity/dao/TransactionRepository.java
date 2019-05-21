package com.example.springsecurity.dao;


import com.example.springsecurity.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
