package com.example.springsecurity.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int transactionId;
    private String transactionType;
    private LocalDate transactionDate;
    private double amount;


//    @ManyToOne
    private String fromWho;

//    @ManyToOne
    private String toWho;


    @ManyToOne(targetEntity = Account.class)
    private Account account;
    @ManyToOne(targetEntity = Teller.class)
    private Teller teller;


    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Teller getTeller() {
        return teller;
    }

    public void setTeller(Teller teller) {
        this.teller = teller;
    }
}
