package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.LoginRepository;
import com.example.springsecurity.model.Login;
import com.example.springsecurity.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
//import sun.rmi.runtime.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class LogInServiceImp  implements UserDetailsService,LogInService{
    @Autowired
    LoginRepository loginRepository;

    @Override
    public int checkLogin(String email, String password) {
        Login userName=loginRepository.findByUserName(email);
        if(userName==null)
        {
                return 2;
        }
        else
        {
            Login passwordCheck=loginRepository.findByPassword(userName.getPassword());
            if(!passwordCheck.getPassword().equals(password))
            {
                return 1;
            }
        }

        return 3;
    }

    public Login login(String email,String password)
    {
            Login userName=loginRepository.findByUserName(email);
            return userName;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Login userInfo=loginRepository.findByUserName(userName);
        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
        User user = new User(userInfo.getUserName(),userInfo.getPassword(), Arrays.asList(authority));
        UserDetails userDetails = (UserDetails)user;

        return userDetails;
    }
}
