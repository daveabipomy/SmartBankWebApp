package com.example.springsecurity.Controller;


import com.example.springsecurity.dao.CustomerRepository;
import com.example.springsecurity.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manager")
public class ManagerController {


    @Autowired
    CustomerRepository customerRepository;


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
        System.out.println("testtttttttttt "+id);
        System.out.println("testtttttttttt "+firstName);
        return  "/Approve";
    }


//    @RequestMapping(value = "/customer/{id}/{accountType}",method=RequestMethod.POST)
//    public String userDetail(@PathVariable("id") Long id,@PathVariable("accountType") String accountType, Model model){
////        System.out.println(+id);
////        System.out.println(accountType);
//        Optional<Customer> customer = customerRepository.findById(id);
//        model.addAttribute("customerdetail", customer);
//        return "CustomerDetail";
//    }

    @GetMapping("/empAccount")
    public String empAccount(){
        return "/manager/CreateEmpAcc";
    }

    @GetMapping("/generateStatement")
    public String generateStatement(){
        return "/manager/generateStatement";
    }

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
