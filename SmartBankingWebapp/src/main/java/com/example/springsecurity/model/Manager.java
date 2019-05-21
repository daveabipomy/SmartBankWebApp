package com.example.springsecurity.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Manager extends  Employee{
    private  double salary;

    @ManyToOne(targetEntity = Branch.class)
    private Branch branchm;
}
