package com.example.springsecurity.service.impl;


import com.example.springsecurity.dao.AccountRepostitory;
import com.example.springsecurity.dao.TransactionRepository;
import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Transaction;
import com.example.springsecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TransactionServiceImpl {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepostitory accountRepostitory;

    @Autowired
    AccountService accountService;



    public void deposit(String accountNumber, double amount) {
        Transaction transaction = new Transaction();
        Account account = accountService.findAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAccount(account);
        transaction.setTransactionType("Deposit");
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance() + amount);
        account.getTransactions().add(transaction);
        accountService.updateBalance(account);
    }

    public boolean withdraw(String accountNumber, double amount) {
        Account account = accountService.findAccount(accountNumber);
        if(account.getBalance() > amount) {
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAccount(account);
            transaction.setTransactionType("Withdraw");
            transactionRepository.save(transaction);
            account.setBalance(account.getBalance() - amount);
            account.getTransactions().add(transaction);
            accountService.updateBalance(account);
            return true;
        }
        return false;
    }


    public void transfer(String accountNumber, String toAccountNumber, double amount) {

        Account account = accountService.findAccount(accountNumber);
        if(account.getBalance() > amount) {
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAccount(account);
            transaction.setToWho(toAccountNumber);
            transaction.setTransactionType("Transfer");
            transactionRepository.save(transaction);
            account.setBalance(account.getBalance() - amount);
            Account toAccount = accountRepostitory.findByAccountNumber(toAccountNumber);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountService.updateBalance(account);
            accountService.updateBalance(toAccount);
        }

    }
}
