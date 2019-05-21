package com.example.springsecurity.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Login {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
//    @NotNull(message = "User Name may not be null")
    @NotEmpty(message = "*Please provide your User name")
    @Column(unique = true)
    @Email(message="{errors.invalid_email}")
    private String userName;
    @NotEmpty(message = "*Please provide your password")
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
