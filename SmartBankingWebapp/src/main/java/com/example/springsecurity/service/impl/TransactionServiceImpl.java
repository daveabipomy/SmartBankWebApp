package com.example.springsecurity.service.impl;


import com.example.springsecurity.dao.*;
import com.example.springsecurity.model.*;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import sun.rmi.runtime.Log;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    @Autowired
    TellerRepository tellerRepository;

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
        return transactions.stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).collect(Collectors.toList());
    }
    public List<Transaction> generateStatement(String accountNumber,LocalDate startDate, LocalDate endDate)
    {
        Account accounts=accountRepostitory.findByAccountNumber(accountNumber);
        List<Transaction> transactions=new ArrayList<>();

            if(accounts!=null) {
              return transactionRepository.findTransactionByAccount(accounts).stream().filter(x->x.getTransactionDate().compareTo(startDate)>0 && x.getTransactionDate().compareTo(endDate)<0).sorted(Comparator.comparing(Transaction::getTransactionDate)).collect(Collectors.toList());
            }
        return transactions;
    }
    public void deposit(String accountNumber, double amount,String username) {
        Transaction transaction = new Transaction();
        Account account = accountService.findAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAccount(account);
        transaction.setTransactionType("Deposit");
        transaction.setTeller(getTeller(username));
        transactionRepository.save(transaction);
        account.setBalance(account.getBalance() + amount);
        account.getTransactions().add(transaction);
        accountService.updateBalance(account);

    }

    public Teller getTeller(String username)
    {
        Login login=loginRepository.findByUserName(username);
        return  tellerRepository.findTellerByLogin(login);
    }
    public void depositAtm(String accountNumber, double amount) {
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



    public boolean withdrawATM(String accountNumber, double amount) {
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

    public boolean withdraw(String accountNumber, double amount,String userName) {
        Account account = accountService.findAccount(accountNumber);
        if(account.getBalance() > amount) {
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAccount(account);
            transaction.setTransactionType("Withdraw");
            transaction.setTeller(getTeller(userName));
            transactionRepository.save(transaction);
            account.setBalance(account.getBalance() - amount);
            account.getTransactions().add(transaction);
            accountService.updateBalance(account);
            return true;
        }
        return false;
    }


    public void transferATM(String accountNumber, String toAccountNumber, double amount) {

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

    public void transfer(String accountNumber, String toAccountNumber, double amount,String userName) {

        Account account = accountService.findAccount(accountNumber);
        if(account.getBalance() > amount) {
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAccount(account);
            transaction.setToWho(accountService.findAccount(toAccountNumber));
            transaction.setTransactionType("Transfer");
            transaction.setTeller(getTeller(userName));
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
