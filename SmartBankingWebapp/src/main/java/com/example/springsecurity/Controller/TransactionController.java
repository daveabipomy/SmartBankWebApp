package com.example.springsecurity.Controller;


import com.example.springsecurity.model.Account;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.impl.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    AccountService accountService;



    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ModelAndView deposit(@RequestParam("accountNumber") String accountNumber,
                                @RequestParam("amount") double amount,HttpServletRequest request)
    {
        String username = request.getRemoteUser();
         Account account = accountService.findAccount(accountNumber);
         ModelAndView modelAndView = new ModelAndView();
         if(account == null){
             modelAndView.addObject("transactionmessage","Account Doesn't Exist!");
             modelAndView.setViewName("teller/deposit");
         }else if(amount == 0){
             modelAndView.addObject("transactionmessage","Insert Amount");
             modelAndView.setViewName("teller/deposit");
         }
         else{
             double preBalance = accountService.findAccount(accountNumber).getBalance();
             transactionService.deposit(accountNumber,amount,username);
             modelAndView.addObject("transactionmessage","Deposited Successfuly!");
             modelAndView.addObject("preBalance", preBalance);
             modelAndView.addObject("account", account);
             modelAndView.addObject("accountType", account.getAccountType());
             modelAndView.addObject("openingDate", account.getOpeneingDate());
             modelAndView.addObject("closingDate", account.getClosingDate());
             modelAndView.addObject("balance", account.getBalance());
             modelAndView.addObject("firstName", account.getCustomer().getFirstName());
             modelAndView.addObject("lastName", account.getCustomer().getLastName());
             modelAndView.addObject("dateOfBirth", account.getCustomer().getDob());
             modelAndView.addObject("phoneNumber", account.getCustomer().getPhone());
             modelAndView.addObject("photo", account.getCustomer().getPhoto());
             modelAndView.setViewName("teller/deposit");
         }
         return  modelAndView;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ModelAndView withdraw(@RequestParam("waccountNumber") String accountNumber,
                                 @RequestParam("wamount") double amount,HttpServletRequest request)
    {
        String username = request.getRemoteUser();


        Account account = accountService.findAccount(accountNumber);
        ModelAndView modelAndView = new ModelAndView();
        if(account == null){
            modelAndView.addObject("transactionmessage","Account Doesn't Exist!");
            modelAndView.setViewName("teller/withdraw");
        }else if(amount == 0){
            modelAndView.addObject("transactionmessage","Insert Amount");
            modelAndView.setViewName("teller/withdraw");
        }else if(account.getBalance() < amount){
            modelAndView.addObject("transactionmessage","Insufficient Balance");
            modelAndView.setViewName("teller/deposit");
        }
        else{
            double preBalance = accountService.findAccount(accountNumber).getBalance();
            transactionService.withdraw(accountNumber,amount,username);
            modelAndView.addObject("transactionmessage","Withdrawed Successfuly!");
            modelAndView.addObject("preBalance", preBalance);
            modelAndView.addObject("account", account);
            modelAndView.addObject("accountType", account.getAccountType());
            modelAndView.addObject("openingDate", account.getOpeneingDate());
            modelAndView.addObject("closingDate", account.getClosingDate());
            modelAndView.addObject("balance", account.getBalance());
            modelAndView.addObject("firstName", account.getCustomer().getFirstName());
            modelAndView.addObject("lastName", account.getCustomer().getLastName());
            modelAndView.addObject("dateOfBirth", account.getCustomer().getDob());
            modelAndView.addObject("phoneNumber", account.getCustomer().getPhone());
            modelAndView.addObject("photo", account.getCustomer().getPhoto());
            modelAndView.setViewName("teller/withdraw");
        }
        return  modelAndView;

    }


    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ModelAndView transfer(@RequestParam("taccountNumber") String accountNumber,
                         @RequestParam("toaccountNumber") String toAccountNumber,
                         @RequestParam("tamount") double amount,HttpServletRequest request)
    {
        String username = request.getRemoteUser();

        Account account = accountService.findAccount(accountNumber);
        Account toaccount = accountService.findAccount(toAccountNumber);
        ModelAndView modelAndView = new ModelAndView();
        if(account == null){
            modelAndView.addObject("transactionmessage","Account Doesn't Exist!");
            modelAndView.setViewName("teller/transfer");
        }else if(amount == 0){
            modelAndView.addObject("transactionmessage","Insert Amount");
            modelAndView.setViewName("teller/transfer");
        }else if(account.getBalance() < amount){
            modelAndView.addObject("transactionmessage","Insufficient Balance");
            modelAndView.setViewName("teller/transfer");
        }else if(toaccount == null){
            modelAndView.addObject("tomessage","Account Doesn't Exist!");
            modelAndView.setViewName("teller/transfer");
        }
        else{
            double preBalance = accountService.findAccount(accountNumber).getBalance();
            double toPreBalance = accountService.findAccount(toAccountNumber).getBalance();
            transactionService.transfer(accountNumber,toAccountNumber,amount,username);
            modelAndView.addObject("transactionmessage","Transfered Successfuly!");
            modelAndView.addObject("preBalance", preBalance);
            modelAndView.addObject("account", account);
            modelAndView.addObject("accountType", account.getAccountType());
            modelAndView.addObject("balance", account.getBalance());
            modelAndView.addObject("firstName", account.getCustomer().getFirstName());
            modelAndView.addObject("lastName", account.getCustomer().getLastName());
            modelAndView.addObject("photo", account.getCustomer().getPhoto());

            modelAndView.addObject("toaccountType", toaccount.getAccountType());
            modelAndView.addObject("tobalance", toaccount.getBalance());
            modelAndView.addObject("tofirstName", toaccount.getCustomer().getFirstName());
            modelAndView.addObject("tolastName", toaccount.getCustomer().getLastName());
            modelAndView.addObject("topreBalance", toPreBalance);

            modelAndView.setViewName("teller/transfer");
        }
        return  modelAndView;

    }



//    @RequestMapping(value = "/transferCustomer", method = RequestMethod.POST)
    @RequestMapping(value = "/transferCustomer", method = RequestMethod.POST)
    public ModelAndView transferCustomer(@RequestParam("accountNumber") String accountNumber,
                                 @RequestParam("amount") double amount,HttpServletRequest request)
    {
        String username = request.getRemoteUser();

//        Account account = accountService.findAccount();
        Account account=accountService.findAccountByUserName(username);
        Account toaccount = accountService.findAccount(accountNumber);
        ModelAndView modelAndView = new ModelAndView();
        if(account == null){
            modelAndView.addObject("transactionmessage","Account Doesn't Exist!");
            modelAndView.setViewName("customer/transfer");
        }else if(amount == 0){
            modelAndView.addObject("transactionmessage","Insert Amount");
            modelAndView.setViewName("customer/transfer");
        }else if(account.getBalance() < amount){
            modelAndView.addObject("transactionmessage","Insufficient Balance");
            modelAndView.setViewName("customer/transfer");
        }else if(toaccount == null){
            modelAndView.addObject("tomessage","Account Doesn't Exist!");
            modelAndView.setViewName("customer/transfer");
        }
        else{
            double preBalance = accountService.findAccountByUserName(username).getBalance();
            double toPreBalance = accountService.findAccount(accountNumber).getBalance();
            transactionService.transfer(accountService.findAccountByUserName(username).getAccountNumber(),accountNumber,amount,username);
            modelAndView.addObject("transactionmessage","Transfered Successfuly!");
            modelAndView.addObject("preBalance", preBalance);
            modelAndView.addObject("account", account);
            modelAndView.addObject("accountType", account.getAccountType());
            modelAndView.addObject("balance", account.getBalance());
            modelAndView.addObject("firstName", account.getCustomer().getFirstName());
            modelAndView.addObject("lastName", account.getCustomer().getLastName());
            modelAndView.addObject("photo", account.getCustomer().getPhoto());

            modelAndView.addObject("toaccountType", toaccount.getAccountType());
            modelAndView.addObject("amount", amount);
            modelAndView.addObject("tofirstName", toaccount.getCustomer().getFirstName());
            modelAndView.addObject("tolastName", toaccount.getCustomer().getLastName());


            modelAndView.setViewName("customer/transfer");
        }
        return  modelAndView;

    }


}
