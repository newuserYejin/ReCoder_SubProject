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

    @GetMapping("/booking")
    public String bookingPage() {
        return "booking";
    }

    @GetMapping("/inquiry")
    public String inquiryPage(){
        return "inquiry";
    }

    @GetMapping("/approvalMain")
    public String approvalMainController(){
        return "approvalMain";
    }

    @GetMapping("/board")
    public String BoardPage(){
        return "board";
    }

    @GetMapping("/login")
    public void loginPage(){};
}
