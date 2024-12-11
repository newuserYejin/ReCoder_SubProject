package com.ohgiraffers.refactorial.user.model.service;

import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

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

    public String findDeptName(int deptCode) {

        String deptName = userMapper.findDeptName(deptCode);

        return deptName;
    }

    public String findPositionName(int positionValue) {
        String positionName = userMapper.findPositionName(positionValue);

        return positionName;
    }

    public boolean pwMatch(String insertPW, String currentPW) {
        return encoder.matches(insertPW,currentPW);
    }

    public Integer changePw(String changePW, String empId) {

        String enChangePW = encoder.encode(changePW);

        Map<String, String> updateData = new HashMap<>();
        updateData.put("enChangePW",enChangePW);
        updateData.put("empId",empId);

        int result =  userMapper.changePW(updateData);

        return result;
    }

    @Transactional
    public Integer updatePersonalInfo(String email, String phone, String address, String userId) {

        if (email == null && phone == null && address == null) {
            System.out.println("업데이트할 데이터가 없습니다.");
            return 0; // 업데이트 실행 안 함
        }

        Map<String, String> updateData = new HashMap<>();

        updateData.put("email",email);
        updateData.put("phone",phone);
        updateData.put("address",address);
        updateData.put("userId",userId);

        int result = userMapper.updatePersonalInfo(updateData);

        return result;
    }
}
