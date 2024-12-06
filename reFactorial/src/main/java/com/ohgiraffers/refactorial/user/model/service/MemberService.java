package com.ohgiraffers.refactorial.user.model.service;

import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int addEmployee(UserDTO userDTO) {
        // DB에 insert 하기 전 service 계층에서 값을 가공(암호화)해야 한다.
        userDTO.setEmpPwd(encoder.encode(userDTO.getEmpPwd()));

        int result = userMapper.addEmployee(userDTO);

        return result;
    }

    // 사용자가 입력한 ID를 입력받아 회원을 조회하는 메소드
    public UserDTO findUserId(String username) {

        System.out.println("username = " + username);

        UserDTO user = userMapper.findByUsername(username);

        if (user == null){
            return null;
        } else{
            return user;
        }
    }
}
