package com.ohgiraffers.refactorial.user.model.service;

import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserMapper userMapper;

    // 사용자가 입력한 ID를 입력받아 회원을 조회하는 메소드
    public LoginUserDTO findByUsername(String username) {

        LoginUserDTO login = userMapper.findByUsername(username);

        if (login == null){
            return null;
        } else{
            return login;
        }
    }

}
