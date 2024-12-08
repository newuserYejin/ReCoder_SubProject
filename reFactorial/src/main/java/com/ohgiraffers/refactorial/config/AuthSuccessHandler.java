package com.ohgiraffers.refactorial.config;

import com.ohgiraffers.refactorial.auth.model.AuthDetails;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

//@Component
//public class AuthSuccessHandler implements AuthenticationSuccessHandler {
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
//        UserDTO user = authDetails.getUser();
//
//        request.getSession().setAttribute("LoginUserInfo", user);
//
//        System.out.println("user = " + user);
//        response.sendRedirect("/user");
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//    }
//}

@Configuration
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//    public AuthSuccessHandler() {
//        super();
//    }
//
//    public AuthSuccessHandler(String defaultTargetUrl) {
//        super(defaultTargetUrl);
//    }

    @Override
    @ModelAttribute("LoginUserInfo")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
        UserDTO user = authDetails.getUser();

        request.getSession().setAttribute("LoginUserInfo", user);

        System.out.println("세션에 저장할 user = " + user);

        response.sendRedirect("/user");
    }
}
