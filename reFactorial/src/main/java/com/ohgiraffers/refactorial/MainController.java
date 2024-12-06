package com.ohgiraffers.refactorial;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class    MainController {

    @GetMapping("/")
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
        return "approvals/approvalMain";
    }

    @GetMapping("/board")
    public String BoardPage(){
        return "board";
    }

    @GetMapping("sharedWork")
    public String sharedWork(){
        return "sharedWork";
    }
  
}
