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

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        String id = authentication.getName();
//
//        // AuthDetails 객체 가져오기
//        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
//
//        UserDTO user = authDetails.getUser(); // AuthDetails 내부의 UserDTO 객체
//
//        // 사용자 ID를 모델에 추가
//        model.addAttribute("userId", user.getEmpId());  // 또는 user.getEmpId(), user.getUsername() 등을 사용
//
//        // 역할 정보 가져오기 (예시)
//        model.addAttribute("roles", authDetails.getAuthorities());

        // 현재 인증된 사용자 정보를 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // AuthDetails 객체 가져오기
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();

        // AuthDetails 객체에서 사용자 정보 가져오기
        UserDTO user = authDetails.getUser(); // AuthDetails 내부의 UserDTO 객체

        // 사용자 ID를 모델에 추가
        model.addAttribute("userId", user.getEmpId());  // 또는 user.getEmpId(), user.getUsername() 등을 사용

        // 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = authDetails.getAuthorities();

        // 권한 정보를 모델에 추가
        model.addAttribute("roles", authorities);


        System.out.println("user = " + user);

        model.addAttribute("userDTO", user);

        return "/index";
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
}

