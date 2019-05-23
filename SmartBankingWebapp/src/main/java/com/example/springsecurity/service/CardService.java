package com.example.springsecurity.service;

import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Card;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Login;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CardService {
    List<Card> findAll();
    Card save(Card student);
    Card findOne(Long id);
}
