package com.ohgiraffers.refactorial;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("user")
    public String mainControll(){
        return "index";
    }

    @GetMapping("/login")
    public void loginPage(){};
}
