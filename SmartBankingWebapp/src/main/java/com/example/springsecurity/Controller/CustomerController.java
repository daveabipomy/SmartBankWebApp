package com.example.springsecurity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/customerDetail")
    public String showIndex(){
        return "/customer/checkCustomerDetail";
    }

    @GetMapping("/checkingBalance")
    public String checkingBalance(){
        return "/customer/checkingBalance";
    }

    @GetMapping("/transfer")
    public String transfer(){
        return "/customer/transfer";
    }

    @GetMapping("/viewStatement")
    public String viewStatement(){
        return "/customer/viewStatement";
    }

    @GetMapping("/transaction")
    public String transaction(){
        return "/customer/transaction";
    }
}
