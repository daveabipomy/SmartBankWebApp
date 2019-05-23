package com.example.springsecurity.dao;

import com.example.springsecurity.model.Login;
import com.example.springsecurity.model.Teller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TellerRepository extends JpaRepository<Teller,Long> {
    public Teller findByEmployeeId(String empid);
    public Teller findTellerByLogin(Login login);
}
