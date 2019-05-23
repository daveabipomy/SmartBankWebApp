package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.*;
import com.example.springsecurity.model.*;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.CardService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
public class CardServiceImp implements CardService {


    @Autowired
    private CardRepostitory cardRepostitory;


    @Override
    public List<Card> findAll() {
        return cardRepostitory.findAll();
    }

    @Override
    public Card save(Card c) {
        return cardRepostitory.save(c);
    }

    @Override
    public Card findOne(Long id) {
        return cardRepostitory.getOne(id);
    }


}
