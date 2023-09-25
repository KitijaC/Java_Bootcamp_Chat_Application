package com.chatapplication.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String displayLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String displayRegisterPage(){
        return "register";
    }

}
