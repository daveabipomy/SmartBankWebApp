package com.example.springsecurity.dao;


import com.example.springsecurity.model.Login;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends CrudRepository<Login,Long> {

    public Login findByUserName(String userName);
    public Login findByPassword(String password);
//    public Login findByUserNameAndAndPassword(String userName,String password);
}
