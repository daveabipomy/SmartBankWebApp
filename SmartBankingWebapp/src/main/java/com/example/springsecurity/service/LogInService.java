package com.example.springsecurity.service;

import com.example.springsecurity.model.Login;

public interface LogInService {
    public int checkLogin(String email, String password);
    public Login login(String email,String password);
}
