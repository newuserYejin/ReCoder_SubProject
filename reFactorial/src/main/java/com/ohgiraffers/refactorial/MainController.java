package com.ohgiraffers.refactorial;

import com.ohgiraffers.refactorial.user.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/user")
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

    @GetMapping("sharedWork")
    public String sharedWork(){
        return "sharedWork";
    }

    @GetMapping("/auth/login")
    public void loginPage(){};

}
