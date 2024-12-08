package com.ohgiraffers.refactorial;

import com.ohgiraffers.refactorial.auth.model.AuthDetails;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;

@ControllerAdvice
public class CommonController {

//    @ModelAttribute("LoginUserInfo")
//    public UserDTO LoginUserInfo(){
//
//        // 현재 인증된 사용자 정보를 가져오기
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////
////        // AuthDetails 객체 가져오기
////        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
////
////        // AuthDetails 객체에서 사용자 정보 가져오기
////        UserDTO user = authDetails.getUser(); // AuthDetails 내부의 UserDTO 객체
//
//        // 사용자 ID를 모델에 추가
////        model.addAttribute("userId", user.getEmpId());  // 또는 user.getEmpId(), user.getUsername() 등을 사용
////
////        // 권한 정보 가져오기
////        Collection<? extends GrantedAuthority> authorities = authDetails.getAuthorities();
////
////        // 권한 정보를 모델에 추가
////        model.addAttribute("roles", authorities);
////        model.addAttribute("userDTO", user);
//
////        System.out.println("user = " + user);
////
////        return user;
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Authentication is available: " + authentication);
//
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
//            return new UserDTO(); // 기본값으로 빈 UserDTO 반환
//        }
//        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
//        return authDetails.getUser();
//    }

    @ModelAttribute("LoginUserInfo")
    public UserDTO LoginUserInfo(HttpSession session) {
        System.out.println("session.getAttribute(\"LoginUserInfo\") = " + session.getAttribute("LoginUserInfo"));

        UserDTO user = (UserDTO) session.getAttribute("LoginUserInfo");

        if (user == null) {
            System.out.println("No user information in session, returning default");
            return new UserDTO();
        }

        return user;
    }
}
