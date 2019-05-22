package com.example.springsecurity.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teller")
public class TellerController {
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


    @GetMapping("/viewStatement")
    public String viewStatement(){
        return "/teller/viewStatement";
    }
}
