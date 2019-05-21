package com.example.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int cardnumber;
    private LocalDate expireDate;

    @OneToMany(mappedBy = "card",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @JsonManagedReference(value="account-card")
    @Column(name = "card",nullable = false)
    private List<Account> accounts;
}
