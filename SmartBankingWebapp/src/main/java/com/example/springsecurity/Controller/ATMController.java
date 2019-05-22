package com.example.springsecurity.Controller;

import com.example.springsecurity.model.Account;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.TransactionService;
import com.example.springsecurity.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/smart/atm")
public class ATMController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionServiceImpl transactionService;

    @PostMapping("/balance")
    public @ResponseBody String  checkBalance(@RequestParam("accountNumber") String accountNumber, @RequestParam("pin") long pin){
        Account account = accountService.findAccount(accountNumber);
        if(account!= null && pin == 1234)
            return String.valueOf(accountService.findAccount(accountNumber).getBalance());
        else
            return "Invalid Pin";
    }

    @PostMapping("/withdraw")
    public @ResponseBody String  withdraw(@RequestParam("accountNumber") String accountNumber, @RequestParam("pin") long pin, @RequestParam("amount") double amount){
        Account account = accountService.findAccount(accountNumber);

        if(account!= null && pin == 1234) {
            double preBalance = account.getBalance();
            transactionService.withdraw(accountNumber,amount);
            return "Withdraw Successful!" + "\n" +
                    "Your Previous Balance is: " + preBalance + "\n" +
                    "Your Current Balance is: " + String.valueOf(accountService.findAccount(accountNumber).getBalance());
        }else
            return "Invalid Pin";
    }

    @PostMapping("/deposit")
    public @ResponseBody String  deposit(@RequestParam("accountNumber") String accountNumber, @RequestParam("pin") long pin, @RequestParam("amount") double amount){
        Account account = accountService.findAccount(accountNumber);

        if(account!= null && pin == 1234) {
            double preBalance = account.getBalance();
            transactionService.deposit(accountNumber,amount);
            return "Deposit Successful!" + "\n" +
                    "Your Previous Balance is: " + preBalance + "\n" +
                    "Your Current Balance is: " + String.valueOf(accountService.findAccount(accountNumber).getBalance());
        }else
            return "Invalid Account / Pin";
    }

    @PostMapping("/transfer")
    public @ResponseBody String  transfer(@RequestParam("fromAccountNumber") String accountNumber,@RequestParam("toAccountNumber") String toaccountNumber, @RequestParam("pin") long pin, @RequestParam("amount") double amount){
        Account account = accountService.findAccount(accountNumber);

        if(account!= null && pin == 1234) {
            double preBalance = account.getBalance();
            transactionService.transfer(accountNumber,toaccountNumber,amount);
            return "$"+ amount + " transfered to Account No.: " + toaccountNumber + "\n" +
                    "Your Previous Balance is: " + preBalance + "\n" +
                    "Your Current Balance is: " + String.valueOf(accountService.findAccount(accountNumber).getBalance());
        }else
            return "Invalid Account / Pin";
    }

}
