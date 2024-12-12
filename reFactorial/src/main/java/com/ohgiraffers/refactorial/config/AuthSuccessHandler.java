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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date stringToDate(String dateStr) throws ParseException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(MYSQL_DATE_FORMAT);
        return formatter.parse(dateStr);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(MYSQL_DATE_FORMAT);
        return formatter.format(date);
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(MYSQL_DATE_FORMAT);
        return formatter.format(new Date());
    }

    @Override
    @ModelAttribute("LoginUserInfo")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthDetails authDetails = (AuthDetails) authentication.getPrincipal();
        UserDTO user = authDetails.getUser();

        request.getSession().setAttribute("LoginUserInfo", user);

        String saveIdParam = request.getParameter("saveId");
        boolean rememberMe = (saveIdParam != null && saveIdParam.equals("true"));

        String memberId = user.getEmpId();

        // 사번 저장 관려
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

        Date today = new Date();

        System.out.println("LoginUserInfo = " + user);

        System.out.println("today = " + dateToString(today));
        System.out.println("time = " + getCurrentDateTime());

        response.sendRedirect("/user");
    }


}
