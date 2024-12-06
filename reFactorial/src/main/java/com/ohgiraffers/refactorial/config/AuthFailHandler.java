package com.ohgiraffers.refactorial.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

// 사용자 로그인 실패시 실패 요청 핸들러 메세지 처리 및 페이지 경로 처리
@Configuration
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = null;

        if (exception instanceof BadCredentialsException){
            // 사용자의 아이디가 존재하지 않거나 아이디와 비밀번호가 일치하지 않을 경우 발생하는 exception
            errorMessage = "아이디 혹은 비밀번호를 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            // 서버에서 사용자 정보 검증 과정에서 에러 발생
            errorMessage = "서버 내부에서 오류가 발생했습니다.";
        } else if (exception instanceof UsernameNotFoundException){
            // db 에 사용자의 정보가 존재하지 않을 때 발생하는 오류
            errorMessage = "존재하지 않는 정보입니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            // 보안 컨택스트에 인증 객체가 존재하지 않거나, 인증 정보가 없는 상태에서 접근하는 경우
            errorMessage = "인증이 거부되었습니다.";
        } else {
            errorMessage = "알 수 없는 에러 발생!!!!!!!!!! 비상!!!!!!!!!!!!";
        }

        // 에러 메세지를 URL 을 통해 전달
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");

        // 오류 발생시 이동 페이지 URL
        setDefaultFailureUrl("/login?message=" + errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
