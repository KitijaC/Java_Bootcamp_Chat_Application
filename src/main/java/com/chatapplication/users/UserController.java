package com.chatapplication.users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
    public String handleUserLogin(UserLoginRequest userLoginRequest, HttpServletResponse response) {
        try {
            UserEntity user = this.userService.verifyUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
            if (user == null) throw new Exception("Username or password is not correct");

            // create a cookie and save user id to the cookie / session
            Cookie cookie = new Cookie("loggedInUserId", user.getId().toString());

            // cookie expiry time in seconds
            // this means how long information will be saved before deleting
            cookie.setMaxAge(100000);
            // saving the cookie to the http request so that it can be stored on the users browser
            response.addCookie(cookie);

            return "redirect:/chat-room";
        } catch (Exception exception) {
            return "redirect:/login?status=LOGIN_FAILED&error=" + exception.getMessage();
        }
    }

    @GetMapping("/logout")
    public String handleLogout(
            @CookieValue(value = "loggedInUserId", defaultValue = "") String userId,
            HttpServletResponse response
    ){
        Cookie cookie = new Cookie("loggedInUserId", null);
        cookie.setMaxAge(0); // expire the cookie = deleting the cookie
        response.addCookie(cookie);

        /** HOW TO ADD MORE COOKIES:
        * if you want to add more cookies with other useful info you can simply:
         * Cookie cookie1 = new Cookie("Something", "some value");
         * cookie1.setMaxAge(5000);
         * Cookie cookie2 = new Cookie("SomethingElse", "123");
         * cookie2.setMaxAge(60); // valid for 60 sec
         * Cookie cookie3 = new Cookie("AnotherThing", "another value");
         * cookie3.setMaxAge(1000);
         *
         * response.addCookie(cookie1);
         * response.addCookie(cookie2);
         * response.addCookie(cookie3);
        **/

        return "redirect:/login?status=LOGOUT_SUCCESSFUL";
    }

    @GetMapping("/profiles")
    public String displayProfilesPage(Model model) {
        model.addAttribute("members", this.userService.getAllUsers());
        return "profiles";
    }

}
