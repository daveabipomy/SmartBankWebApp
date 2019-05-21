package com.example.springsecurity.Controller;



import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Customer;
import com.example.springsecurity.model.Login;
//import com.example.springsecurity.model.User;
import com.example.springsecurity.service.LogInService;
import com.example.springsecurity.service.impl.AccountServiceImp;
import com.example.springsecurity.service.impl.UploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/smart")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountServiceImp accountService;


    @Autowired
    private UploadServiceImpl uploadService;
    @Autowired
    private LogInService logInService;


    @RequestMapping(value = "/customer",method = RequestMethod.GET)
    public String requestForm(Customer customer){
    return "NewCustomerRequestForm";
    }
    @RequestMapping(value = "/afterLogin/customer",method = RequestMethod.GET)
    public String custome(Customer customer){
        return "/afterLogin/customer";
    }
    @RequestMapping(value = "/afterLogin/teller",method = RequestMethod.GET)
    public String teller(){
        return "/afterLogin/teller";
    }


    @RequestMapping(value = "/afterLogin/manager",method = RequestMethod.GET)
    public String test(Customer customer){
        return "/afterLogin/manager";
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView modelAndView = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        modelAndView.addObject("errorMsg", errorMessage);
        modelAndView.setViewName("error");
        return modelAndView;
    }

//    @GetMapping("/register")
//    public String register(){
//        return "home/register";
//    }

    @RequestMapping(value={"/register"}, method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home/register");
        return modelAndView;
    }



    @RequestMapping(value = {"/register"},method=RequestMethod.POST)
    public  ModelAndView register(@RequestParam String email, @RequestParam String password,@RequestParam String accountnumber)
    {
        ModelAndView modelAndView = new ModelAndView();
        Account checkAccount=accountService.checkAccount(accountnumber);
        if(checkAccount==null)        {
            modelAndView.addObject("failureMessage", "No account number like this");
            modelAndView.setViewName("home/register");
        }
        else {
            Login login = new Login();
            login.setUserName(email);
            login.setPassword(password);
            login.setRole("Customer");
            int approve= accountService.register(login,checkAccount);
            if(approve==1) {
                modelAndView.addObject("successMessage", "You created your login account successfully");
//            modelAndView.addObject("login", new Login());
                modelAndView.setViewName("home/register");
            }
            else if(approve==2)
            {
                modelAndView.addObject("logMessage", "You already created login account, please ");
//            modelAndView.addObject("login", new Login());
                modelAndView.setViewName("home/register");
            }

            else if(approve==4)
            {
                modelAndView.addObject("emailMessage", "this email is already used please choose another");
//            modelAndView.addObject("login", new Login());
                modelAndView.setViewName("home/register");
            }
            else
            {
                modelAndView.addObject("approvalMessage", "You don't get approval yet");
//            modelAndView.addObject("login", new Login());
                modelAndView.setViewName("home/register");
            }
        }
        return modelAndView;

    }

    @RequestMapping(value={"/customer"},headers=("content-type=multipart/*"),method = RequestMethod.POST)
    public ModelAndView requestForm(@Valid Customer customer, BindingResult bindingResult, Model model,@RequestParam("file") MultipartFile file)
    {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("customerrrr "+ customer.getFirstName());
        if (bindingResult.hasErrors()) {
            System.out.println("errorrrrrrrrrr "+ bindingResult.toString());
            modelAndView.setViewName("NewCustomerRequestForm");
//            return "NewCustomerRequestForm";
        }
        else {
            System.out.println("successssssssssss "+ customer.getFirstName());
            accountService.requestNewAccount(customer,file);
            modelAndView.addObject("successMessage", "Customer has been registered successfully");
            modelAndView.addObject("customer", new Customer());
            modelAndView.setViewName("NewCustomerRequestForm");


        }


        return modelAndView;
    }


    @RequestMapping(value = "/login",method=RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/test"},method=RequestMethod.POST)
    public  ModelAndView loginPost(@RequestParam String userName, @RequestParam String password) {
        ModelAndView modelAndView = new ModelAndView();
        int login=logInService.checkLogin(userName,password);
        if(login==1) {
            modelAndView.addObject("passwordMessage", "The password you entered is not correct");
            modelAndView.addObject("login", new Login());
            modelAndView.setViewName("/login");
        }
       else if(login==2) {
            modelAndView.addObject("emailpasswordMessage", "The userName you entered is not correct");
            modelAndView.setViewName("/login");
        }
        else
        {
            Login loginHome=logInService.login(userName,password);
            if(loginHome.getRole().equals("Manager"))
            {
                modelAndView.addObject("HelloMessage", loginHome.getUserName());
                modelAndView.setViewName("/afterLogin/manager");
            }
            else if (loginHome.getRole().equals("Customer"))
            {
                modelAndView.setViewName("/afterLogin/customer");
            }
            else if(loginHome.getRole().equals("Teller"))
            {
                modelAndView.setViewName("/afterLogin/teller");
            }
        }
        return modelAndView;
    }


    @GetMapping("/")
    public String root() {
        return "home/index";
    }


//    @RequestMapping(value="/accountList")
//    public String accountList(Model model) {
//        model.addAttribute("accountList", accountRepository.findAll());
//        return "accountList";
//    }





    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = uploadService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



}
