package com.chatapplication.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // registering this class as a bean
public class UserController {

    private UserService userService;

    @Autowired // Dependency Injection
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String displayLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String displayRegisterPage(){
        return "register";
    }

    @PostMapping("/register")
    public String handleUserRegistration(UserEntity userEntity){
        try {
            this.userService.createUser(userEntity);
            return "redirect:/login?status=REGISTRATION_SUCCESS";
        } catch (Exception exception) {
            return "redirect:/register?status=REGISTRATION_FAILED&error=" + exception.getMessage();
        }
    }

    @PostMapping("/login")
    public String handleUserLogin(UserLoginRequest userLoginRequest) {
        try {
            UserEntity user = this.userService.verifyUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
            if (user == null) throw new Exception("Username or password is not correct");
            return "redirect:/chat-room";
        } catch (Exception exception) {
            return "redirect:/login?status=LOGIN_FAILED&error=" + exception.getMessage();
        }
    }

    @GetMapping("/profiles")
    public String displayProfilesPage(Model model) {
        model.addAttribute("members", this.userService.getAllUsers());
        return "profiles";
    }

//    @GetMapping("/profiles/{id}")
//    public String displayProfile(Model model) {
//        model.addAttribute("user", this.userService.getAllUsers());
//        return "user-profile";
//    }
}
