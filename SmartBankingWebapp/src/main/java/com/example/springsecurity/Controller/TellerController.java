package com.example.springsecurity.Controller;

import com.example.springsecurity.model.Transaction;
import com.example.springsecurity.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/teller")
public class TellerController {
    @Autowired
    TransactionServiceImpl transactionService;
    @GetMapping("/checkBalance")
    public String checkBalance(){
        return "/teller/checkBalance";
    }

    @RequestMapping("/teller-deposit")
    public String deposit(){
        return "/teller/deposit";
    }

    @RequestMapping("/teller-withdraw")
    public String withdraw(){
        return "/teller/withdraw";
    }


    @RequestMapping("/teller-transfer")
    public String transfer(){
        return "/teller/transfer";
    }

    @RequestMapping("/transactionteller")
    public String transactiont(){
        return "/teller/transaction";
    }


//    @GetMapping("/viewStatement")
//    public String viewStatement(){
//        return "/teller/viewStatement";
//    }


    @RequestMapping(value={"/viewStatement"}, method = RequestMethod.GET)
    public String generateStatement(){
        return "/teller/viewStatement";
    }


    @RequestMapping(value={"/viewStatement"}, method = RequestMethod.POST)
    public ModelAndView generateStatementPost(String accountNumber, String startDate, String endDate)
    {
//        LocalDate startDate, LocalDate endDate
        ModelAndView mav=new ModelAndView();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startD = LocalDate.parse(startDate, formatter);
        LocalDate endD = LocalDate.parse(endDate, formatter);
//        LocalDate startDate=LocalDate.of(2019, 4, 1);
//        LocalDate endDate=LocalDate.of(2019, 5, 31);;

        List<Transaction> transactions=transactionService.generateStatement(accountNumber,startD,endD);
        mav.addObject("transactions", transactions);
        mav.setViewName("/teller/viewStatement");
        return mav;

    }
}
