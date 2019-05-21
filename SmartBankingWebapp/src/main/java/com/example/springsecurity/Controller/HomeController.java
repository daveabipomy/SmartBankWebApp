package com.example.springsecurity.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index"})
    public String showIndex(){
        return "home/index";
    }

    @GetMapping("/about")
    public String about(){
        return "home/about";
    }

    @GetMapping("/service")
    public String service(){
        return "home/service";
    }

    @GetMapping("/location")
    public String location(){
        return "home/location";
    }

    @GetMapping("/contact")
    public String contact(){
        return "home/contact";
    }

//    @GetMapping("/register")
//    public String register(){
//        return "home/register";
//    }


    @GetMapping("/test")
    public String test(){
        return "newcustomerrequest";
    }

    @GetMapping("/customerRequest")
    public String customerRequest(){
        return "newcustomerrequest";
    }
}