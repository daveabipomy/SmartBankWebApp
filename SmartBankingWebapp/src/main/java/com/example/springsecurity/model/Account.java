package com.example.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private String accountType;
    private LocalDate openeingDate;
    private  LocalDate closingDate;
    private  String status;
    private double balance;

    @ManyToOne(targetEntity = Customer.class,fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
//    @JsonBackReference
    private Customer customer;

    @ManyToOne(targetEntity = Branch.class)
//    @JsonBackReference
    private  Branch branch;

    @ManyToOne(targetEntity = Card.class)

//    @JsonBackReference
    private Card card;

    @OneToMany(mappedBy = "account",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch=FetchType.LAZY)
    @JsonManagedReference(value="transaction-account")
//    @Column(name = "transaction",nullable = false)
    private List<Transaction> transactions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public LocalDate getOpeneingDate() {
        return openeingDate;
    }

    public void setOpeneingDate(LocalDate openeingDate) {
        this.openeingDate = openeingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
