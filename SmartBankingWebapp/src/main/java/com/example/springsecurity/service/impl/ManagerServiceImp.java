package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.ManagerRepository;
import com.example.springsecurity.model.Login;
import com.example.springsecurity.model.Manager;
import com.example.springsecurity.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImp implements ManagerService {
    @Autowired
    private ManagerRepository managerRepository;


}
