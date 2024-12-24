package com.ohgiraffers.refactorial.auth.service;

import com.ohgiraffers.refactorial.auth.model.AuthDetails;
import com.ohgiraffers.refactorial.common.UserRole;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import com.ohgiraffers.refactorial.user.model.service.MemberService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final MemberService memberService;

    public AuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {

        // 입력한 id가 자동으로 들어오는지 확인하기 위한 sout
        System.out.println("username = " + username);

        LoginUserDTO user = memberService.findUserId(username);

        // 해당 사용자 없음
        if (user == null){
            throw new UsernameNotFoundException("회원 정보가 없습니다.");
        }
        
//        if (UserRole.ACCESSLIMIT.equals(user.getViewAuth())){
//            throw new AuthenticationCredentialsNotFoundException("ACCESSLIMIT");
//        }


        return new AuthDetails(user);
    }
}
