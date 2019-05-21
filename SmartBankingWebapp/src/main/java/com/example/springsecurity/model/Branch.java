package com.example.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int branchId;
    private String branchName;
    private String phone;
    private String email;
//    @OneToOne(nullable=true)
//    @Column(nullable = true)
    @OneToOne
    private Manager manager;



    @OneToMany(mappedBy = "branch",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference(value="employee-branch")
//    @Column(name = "employee",nullable = false)
    private List<Teller> employes;


    @OneToMany(mappedBy = "branchm",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference(value="employee-branch")
//    @Column(name = "employee",nullable = false)
    private List<Manager> employeManager;
}
