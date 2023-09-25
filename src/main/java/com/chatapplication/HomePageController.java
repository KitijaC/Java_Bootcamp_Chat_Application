package com.chatapplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // bean
public class HomePageController {

    @GetMapping("")
    public String displayHomePage(){
        return "index";
    }
}
