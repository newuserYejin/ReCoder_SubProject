package com.ohgiraffers.refactorial.admin.model.service;

import com.ohgiraffers.refactorial.admin.model.dao.AdminMapper;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper am;

    public List<LoginUserDTO> getAllEmployee() {
        return am.getAllEmployee();
    }

    public Integer modifyEmpInfoUpdate(UserDTO userDTO) {

        System.out.println("userDTO = " + userDTO);

        return am.modifyEmpInfoUpdate(userDTO);
//        return 1;
    }
}
