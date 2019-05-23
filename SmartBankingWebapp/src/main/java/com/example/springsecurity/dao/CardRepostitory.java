package com.example.springsecurity.dao;


import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Card;
import com.example.springsecurity.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface CardRepostitory extends JpaRepository<Card, Long> {



}
