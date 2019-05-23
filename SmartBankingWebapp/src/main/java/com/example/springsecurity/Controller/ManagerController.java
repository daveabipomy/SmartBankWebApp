package com.example.springsecurity.Controller;


import com.example.springsecurity.dao.CustomerRepository;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Transaction;
import com.example.springsecurity.service.impl.TransactionServiceImpl;
import com.example.springsecurity.dao.ManagerRepository;
import com.example.springsecurity.model.*;
import com.example.springsecurity.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/manager")
public class ManagerController {


    @Autowired
   private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LogInService logInService;
    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    TransactionServiceImpl transactionService;

    @RequestMapping("/transactionmanager")
    public String transactionm(){
        return "manager/transaction";
    }

    @RequestMapping("/acc")
    public String account(){
        return "manager/account";
    }
    @RequestMapping(value = "/approveNewCustomer", method=RequestMethod.GET)
    public ModelAndView approve()
    {

        ModelAndView mav = new ModelAndView();
        List<Customer> customer = customerService.findAllNotApproved();
        mav.addObject("customer", customer);
        mav.setViewName("manager/approveAccount");

        return mav;
    }


      @RequestMapping(value = "/allAccounts", method=RequestMethod.GET)
    public ModelAndView allaccount()
    {

        ModelAndView mav = new ModelAndView();
        List<Account> account = accountService.findOpenAcc();
        mav.addObject("account", account);
        mav.setViewName("manager/activeAccount");

        return mav;
    }


    @RequestMapping(value = "/Smartacc", method=RequestMethod.GET)
    public ModelAndView smartaccount()
    {
        ModelAndView mav = new ModelAndView();
        List<Customer> customer = customerService.findAllActiveCustomer();
        mav.addObject("customer", customer);
        mav.setViewName("manager/smartCustomer");

        return mav;
    }


    @RequestMapping(value = "/closedAccounts", method=RequestMethod.GET)
    public ModelAndView closedaccount()
    {

        ModelAndView mav = new ModelAndView();
        List<Account> account = accountService.findClosedAcc();
        mav.addObject("account", account);
        mav.setViewName("manager/closeAccount");

        return mav;
    }




    @RequestMapping(value = "/bankcustomer", method=RequestMethod.GET)
    public ModelAndView allcustomer()
    {

        ModelAndView mav = new ModelAndView();
        List<Customer> customer = customerService.findAllApproved();
        mav.addObject("customer", customer);
        mav.setViewName("manager/allcustomers");

        return mav;
    }


    @RequestMapping(value="/approveNewCustomer/{id}", method = RequestMethod.GET)
    public String geteditId(@PathVariable Long id,Model model){
        Customer p=customerService.findById(id);

        if(p!=null){
            model.addAttribute("customer",p);
            return "manager/createCustAcc";
        }
        return"/manager/CreateEmpAcc";
    }
    @RequestMapping(value="/close/{id}", method = RequestMethod.GET)
    public String getcloseId(@PathVariable Long id,Model model){
        Account p=accountService.findById(id);

        if(p!=null){
            model.addAttribute("account",p);
            return "manager/confirmation";
        }
        return"redirect:/manager/bankcustomer";
    }


    @RequestMapping(value = "/close", method=RequestMethod.POST)
    public String closeAccount( @Valid @ModelAttribute("account") Account account,@RequestParam String accountNumber,BindingResult binding, Model model){
        if(binding.hasErrors()){
            model.addAttribute("errors",binding.getAllErrors());
        }

        System.out.println("===================================="+accountNumber);
        Account account1=accountService.findAccount(accountNumber);
        System.out.println("===================================="+account1.getCustomer().getFirstName());
        account1.setClosingDate(LocalDate.now());
        account1.setStatus("closed");
        accountService.save(account1);
        return "manager/approveAccount";
    }

    @RequestMapping(value = "/approveNewCustomer", method=RequestMethod.POST)
    public String aproveAccount(@Valid @ModelAttribute("customer") Customer customer,@RequestParam String accountType, BindingResult binding, Model model, HttpServletRequest request){
       String username = request.getRemoteUser();

       System.out.println("========"+username);

        Login log=logInService.findByUserName(username);
        System.out.println("========"+log.getId());
      Manager m=managerRepository.findManagerByLogin(log);



        if(binding.hasErrors()){
            model.addAttribute("errors",binding.getAllErrors());
        }
        Random rand = new Random();

        customer.setApproval(true);
        customer=customerService.save(customer);

        List<Account> listofAccountBycustom=customer.getAccounts();
        int flag=0;
        for(Account a:listofAccountBycustom){
            if(a.getAccountType().equals("accountType")){
    flag++;
            }
        }
        if(flag==0) {

            Account account1 = new Account();
            account1.setAccountType(accountType);
            account1.setCustomer(customer);
            account1.setOpeneingDate(LocalDate.now());
            account1.setStatus("active");

            String randomNumber = resize(String.valueOf(rand.nextInt(1000)), 3);
            String customerId = resize(String.valueOf(customer.getId()), 3);

            if (accountType.equals("checking")) {
                account1.setAccountNumber(customerId + "01" + randomNumber);
            } else {
                account1.setAccountNumber(customerId + "02" + randomNumber);

            }

           account1.setBranch(m.getBranchm());
            account1.setCard(null);



            accountService.save(account1);
        }else{
            return "manager/approveAccount";
        }


        return "manager/approveAccount";
    }



    @RequestMapping(value = "/approve/{id}/{firstName}",method= RequestMethod.GET)
    public String approve1(@PathVariable Long id,@PathVariable String firstName)
    {
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


}
