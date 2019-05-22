package com.example.springsecurity.Controller;


import com.example.springsecurity.dao.CustomerRepository;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Transaction;
import com.example.springsecurity.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionServiceImpl transactionService;


    @RequestMapping("/transactionmanager")
    public String transactionm(){
        return "manager/transaction";
    }


    @RequestMapping(value = "/approve", method=RequestMethod.GET)
    public ModelAndView approve(Model model)
    {
        ModelAndView mav = new ModelAndView();
        Iterable<Customer> customers = customerRepository.findAll();
        mav.addObject("customers", customers);
        mav.setViewName("/Approve");
        return mav;
    }

    @RequestMapping(value = "/approve/{id}/{firstName}",method= RequestMethod.GET)
    public String approve1(@PathVariable Long id,@PathVariable String firstName)
    {
        return  "/Approve";
    }
    @GetMapping("/empAccount")
    public String empAccount(){
        return "/manager/CreateEmpAcc";
    }

    @RequestMapping(value={"/generateStatement"}, method = RequestMethod.GET)
    public String generateStatement(){
        return "/manager/generateStatement";
    }


    @RequestMapping(value={"/generateStatement"}, method = RequestMethod.POST)
    public ModelAndView generateStatementPost(String accountNumber,String startDate,String endDate)
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
        mav.setViewName("/manager/generateStatement");
        return mav;

    }

//    @RequestMapping(value={"/generateStatement"}, method = RequestMethod.GET)
//    public ModelAndView viewStatement(Model model,HttpServletRequest request){
//        String username = request.getRemoteUser();
//        ModelAndView mav=new ModelAndView();
//        List<Transaction> transactions=transactionService.viewStatement(username);
//        System.out.println("sizeeeeeeeeeee "+transactions);
//        mav.addObject("transactions", transactions);
//        mav.setViewName("/customer/viewStatement");
//        return mav;
//    }

    @GetMapping("/checkBalance")
    public String checkBalance(){
        return "/manager/checkBalance";
    }

    @GetMapping("/closeAccount")
    public String closeAccount(){
        return "/manager/closeAccount";
    }

    @GetMapping("/approveAccount")
    public String approveAccount(){
        return "/manager/approveAccount";
    }

    @GetMapping("/printCard")
    public String showIndex(){
        return "/manager/printCard";
    }
}
