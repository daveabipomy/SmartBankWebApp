package com.example.springsecurity.Controller;

import com.example.springsecurity.model.Transaction;
import com.example.springsecurity.service.TransactionService;
import com.example.springsecurity.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/customerDetail")
    public String showIndex(){
        return "/customer/checkCustomerDetail";
    }

    @GetMapping("/checkingBalance")
    public String checkingBalance(){
        return "/customer/checkingBalance";
    }




    @RequestMapping(value={"/checkingBalance"}, method = RequestMethod.POST)
    public ModelAndView checkBalance(Model model,HttpServletRequest request,String accountType)
    {

        String username = request.getRemoteUser();
        ModelAndView mav=new ModelAndView();
        System.out.println("usernameeee "+username);
        System.out.println("account type "+accountType);
        double balance=transactionService.checkBalance(username,accountType);
        System.out.println("balnceeeee "+balance);
        mav.addObject("balance", balance);
        mav.setViewName("/customer/checkingBalance");
        return mav;
    }

    @GetMapping("/transfer")
    public String transfer(){
        return "/customer/transfer";
    }

//    @GetMapping("/viewStatement")
//    public String viewStatement(){
//        return "/customer/viewStatement";
//    }

    @GetMapping("/transaction")
    public String transaction(){
        return "/customer/transaction";
    }

    @RequestMapping(value={"/viewStatement"}, method = RequestMethod.GET)
    public ModelAndView viewStatement(Model model,HttpServletRequest request){
            String username = request.getRemoteUser();
        ModelAndView mav=new ModelAndView();
        List<Transaction> transactions=transactionService.viewStatement(username);
        System.out.println("sizeeeeeeeeeee "+transactions);
        mav.addObject("transactions", transactions);
        mav.setViewName("/customer/viewStatement");
        return mav;
    }
}
