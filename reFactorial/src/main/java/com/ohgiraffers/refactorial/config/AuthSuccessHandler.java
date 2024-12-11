package com.ohgiraffers.refactorial.config;

import com.ohgiraffers.refactorial.auth.model.AuthDetails;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import com.ohgiraffers.refactorial.user.model.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

@Configuration
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    @ModelAttribute("LoginUserInfo")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
        UserDTO user = authDetails.getUser();

        request.getSession().setAttribute("LoginUserInfo", user);

        String saveIdParam = request.getParameter("saveId");
        boolean rememberMe = (saveIdParam != null && saveIdParam.equals("true"));

        String memberId = user.getEmpId();

        if (rememberMe) {
            Cookie cookie = new Cookie("member_id", memberId);
            cookie.setMaxAge(604800); // 7일 동안 유지
            cookie.setPath("/"); // 전체 도메인에서 유효
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("member_id", "");
            cookie.setMaxAge(0); // 쿠키 삭제
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        response.sendRedirect("/user");
    }
}
