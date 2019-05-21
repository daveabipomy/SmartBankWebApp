package com.example.springsecurity.Controller;


import com.example.springsecurity.service.impl.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/smart")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionServiceImpl transactionService;


    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public void deposit(@RequestParam("accountNumber") String accountNumber,
                          @RequestParam("amount") double amount){

        System.out.println(accountNumber);
         transactionService.deposit(accountNumber,amount);
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public ModelAndView withdraw(Model model, BindingResult bindingResult,@RequestParam("waccountNumber") String accountNumber,
                                 @RequestParam("wamount") double amount){
       ModelAndView modelAndView=new ModelAndView();

        Boolean b=transactionService.withdraw(accountNumber,amount);
       if(b==true)
       {
           modelAndView.addObject("successMessage","transaction success");
       }
       else
       {
           bindingResult.rejectValue("accountNumber","error.account","Account doesn't exist");
       }
       return  modelAndView;

    }


    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public void withdraw(@RequestParam("taccountNumber") String accountNumber,
                         @RequestParam("toaccountNumber") String toAccountNumber,
                         @RequestParam("tamount") double amount){


        transactionService.transfer(accountNumber,toAccountNumber,amount);
    }

}
