package com.example.springsecurity.dao;

import com.example.springsecurity.model.Login;
import com.example.springsecurity.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Long> {

public Manager findManagerByLogin(Login login);

}
