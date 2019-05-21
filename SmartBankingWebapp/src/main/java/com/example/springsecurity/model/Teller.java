package com.example.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Teller extends Employee {

    private double salary;

    @OneToMany(mappedBy = "teller",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference(value="transaction-teller")
    @Column(nullable = false)
    private List<Transaction> transactions;
    @ManyToOne(targetEntity = Branch.class)
    private Branch branch;
}
