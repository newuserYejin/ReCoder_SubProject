package com.ohgiraffers.refactorial;

import com.ohgiraffers.refactorial.auth.model.AuthDetails;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @GetMapping("/user")
    public String mainControll(Model model){
        return "index";
    }

    @GetMapping("/user/booking")
    public String bookingPage() {
        return "/booking/booking";
    }

    @GetMapping("/user/inquiry")
    public String inquiryPage() {
        return "/inquiry/inquiry";
    }

    @GetMapping("/user/approvalMain")
    public String approvalMainController(){
        return "/approvals/approvalMain";
    }

    @GetMapping("/user/notification")
    public String notification() {
        return "/board/notification";
    }

    @GetMapping("/user/sharedWork")
    public String sharedWork(){
        return "/board/sharedWork";
    }
  
    @GetMapping("/auth/login")
    public void loginPage(){};

    @GetMapping("/admin/main")
    public String adminPage(){
        return "admin/admin_main";
    };

    @GetMapping("user/myPage")
    public String myPage(){
        return "myPage/myPage";
    }

}

