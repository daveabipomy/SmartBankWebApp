package com.example.springsecurity.service.impl;


import com.example.springsecurity.dao.AccountRepostitory;
import com.example.springsecurity.dao.CustomerRepository;
import com.example.springsecurity.dao.LoginRepository;
import com.example.springsecurity.dao.TransactionRepository;
import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Login;
import com.example.springsecurity.model.Transaction;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import sun.rmi.runtime.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepostitory accountRepostitory;

    @Autowired
    AccountService accountService;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    CustomerRepository customerRepository;

    public List<Transaction> viewStatement(String userName)
    {
        Login login=loginRepository.findByUserName(userName);
        Customer customer=customerRepository.findCustomerByLogin(login);
        List<Account> accounts=accountRepostitory.findAccountByCustomer(customer);
        List<Transaction> transactions=new ArrayList<>();
        for(Account account:accounts)
        {
            if(transactionRepository.findTransactionByAccount(account)!=null) {
                for (Transaction transaction : transactionRepository.findTransactionByAccount(account)) {
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }
    public List<Transaction> generateStatement(String accountNumber,LocalDate startDate, LocalDate endDate)
    {
        Account accounts=accountRepostitory.findByAccountNumber(accountNumber);
        List<Transaction> transactions=new ArrayList<>();

            if(accounts!=null) {
              return transactionRepository.findTransactionByAccount(accounts).stream().filter(x->x.getTransactionDate().compareTo(startDate)>0 && x.getTransactionDate().compareTo(endDate)<0).collect(Collectors.toList());
            }
        return transactions;
    }
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
//        return true;
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
            transaction.setToWho(accountService.findAccount(toAccountNumber));
            transaction.setTransactionType("Transfer");
            transactionRepository.save(transaction);
            account.setBalance(account.getBalance() - amount);
            Account toAccount = accountRepostitory.findByAccountNumber(toAccountNumber);
            toAccount.setBalance(toAccount.getBalance() + amount);
            accountService.updateBalance(account);
            accountService.updateBalance(toAccount);
        }

    }

    public double checkBalance(String userName,String accountType)
    {
        Login login=loginRepository.findByUserName(userName);
        Customer customer=customerRepository.findCustomerByLogin(login);
        List<Account> accounts=accountRepostitory.findAccountByCustomer(customer);
        double balance=0;
        for(Account account1:accounts)
        {
            if(account1.getAccountType().equals(accountType))
            {
                balance=account1.getBalance();
            }
        }
        return  balance;
    }
}
