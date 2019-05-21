package com.example.springsecurity.Controller;

import com.example.springsecurity.model.Account;
import com.example.springsecurity.model.Login;
import com.example.springsecurity.model.User;
import com.example.springsecurity.service.UserService;
import com.example.springsecurity.service.impl.LogInServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import sun.rmi.runtime.Log;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    LogInServiceImp logInService;

//    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
//    public ModelAndView login(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }


//    @RequestMapping(value = {"/login"},method=RequestMethod.POST)
//    public  ModelAndView loginPost(@RequestParam String email, @RequestParam String password) {
//        ModelAndView modelAndView = new ModelAndView();
//        int login=logInService.checkLogin(email,password);
//        System.out.println("emaillllllll "+email);
//        System.out.println("login statussssssss "+login);
//        if(login==1) {
//            modelAndView.addObject("passwordMessage", "The password you entered is not correct");
////            modelAndView.addObject("login", new Login());
//            modelAndView.setViewName("/login");
//        }
//        if(login==2) {
//            modelAndView.addObject("emailMessage", "The email you entered is not correct");
////            modelAndView.addObject("login", new Login());
//            modelAndView.setViewName("/login");
//        }
//        else
//        {
//            Login loginHome=logInService.login(email,password);
//            if(loginHome.getRole().equals("Admin"))
//            {
//                modelAndView.setViewName("/afterLogin/admin");
//            }
//            else if (loginHome.getRole().equals("Customer"))
//            {
//                modelAndView.setViewName("/afterLogin/customer");
//            }
//            else if(loginHome.getRole().equals("Teller"))
//            {
//                modelAndView.setViewName("/afterLogin/teller");
//            }
//        }
//        return modelAndView;
//    }

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());

        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


}
