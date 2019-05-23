package com.example.springsecurity.Controller;

import com.example.springsecurity.cardgenerator.CreditCardNumberGenerator;
import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Card;
import com.example.springsecurity.model.CardCustomer;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.service.AccountService;
import com.example.springsecurity.service.CardService;
import com.example.springsecurity.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PrintCardController {

    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    CardService cardService;


    @RequestMapping(value = "manager/printCard", method = RequestMethod.GET)
    public ModelAndView cust(@Valid @ModelAttribute("cust") Customer customer, BindingResult result,Model model) {
        List<Customer> customers =
                customerService.findAll().
                        stream().filter(customer1 -> customer1.isApproval())
                        .collect(Collectors.toList());
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());


        }
        List<Customer> mcustomers = new ArrayList<>();
        model.addAttribute("ccust",new CardCustomer());

        int i=1;
        for (Customer cust : customers) {
            List<Account> lsaccount = accountService.findAccountByCustomer(cust);

            if(lsaccount.size()==0){
                Customer mcust=new Customer();
                mcust.setFirstName(cust.getFirstName());
                mcust.setLastName(cust.getLastName());
                mcust.setSex(cust.getSex());
                mcust.setDob(cust.getDob());
                mcust.setCardnumber("XXXXXXXXXXXXXXXX");
                mcust.setAccNumber("N/A");
                mcust.setAccType("N/A");
                mcustomers.add(mcust);
            }

            for(Account account:lsaccount) {
                Customer mcust=new Customer();
                mcust.setFirstName(cust.getFirstName());
                mcust.setLastName(cust.getLastName());
                mcust.setSex(cust.getSex());
                mcust.setDob(cust.getDob());
                //
                if (account.getCard() == null) {
                    mcust.setCardnumber("XXXXXXXXXXXXXXXX");
                } else {

                    Card c = cardService.findOne(account.getCard().getId());
                    //System.err.println(" Accounnt "+account.getAccountNumber()+" CARD ID " + account.getCard().getId()+" "+c.getCardnumber());

                    mcust.setCardnumber(c.getCardnumber());
                }
                mcust.setAccNumber(account.getAccountNumber());
                mcust.setAccType(account.getAccountType());

                mcustomers.add(mcust);
            }

        }
        ModelAndView modelAndView = new ModelAndView();
        CreditCardNumberGenerator cc=new CreditCardNumberGenerator();


        modelAndView.addObject("mcustomers", mcustomers);
        modelAndView.addObject("accounts", accountService.findOpenAcc());
        modelAndView.addObject("cards", cardService.findAll());
        modelAndView.addObject("cardnumber", cc.generate("5400",16));
        //modelAndView.setViewName("manager/printCard");
        modelAndView.setViewName("manager/printCard");
        return modelAndView;
    }
    @RequestMapping(value = "manager/printCard", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("card") Card card,
                       BindingResult result, Model model)  {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            System.out.println(result.getAllErrors());
            model.addAttribute("errors", result.getAllErrors());
            return "manager/printCard";
        }

        cardService.save(card);
        return "redirect:/manager/printCard";
    }
    @RequestMapping(value = "manager/setCard", method = RequestMethod.POST)
    public String setCard(@Valid @ModelAttribute("custcard") CardCustomer cc,
                          BindingResult result, Model model)  {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            System.out.println(result.getAllErrors());
            model.addAttribute("errors", result.getAllErrors());
            return "manager/printCard";
        }
        //System.err.println("XXXXXXXXXX "+ cc.getId()+" card id "+cc.getCardid());
        Customer customer=new Customer();
        customer.setId(Long.valueOf(cc.getId()));

        Account account = accountService.findAccount(cc.getId());
        Card c=new Card();
        c.setId(Long.valueOf(cc.getCardid()));
        account.setCard(c);



        accountService.save(account);
        return "redirect:/manager/printCard";
    }
}
