package com.ohgiraffers.refactorial.admin.model.service;

import com.ohgiraffers.refactorial.admin.model.dao.AdminMapper;
import com.ohgiraffers.refactorial.attendance.dto.AttendanceDTO;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }

    public List<AttendanceDTO> getByDateAtt(String selectedDay, int offset, int size) {

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("selectedDay",selectedDay);
        sendData.put("offset",offset);
        sendData.put("size",size);

        return am.getByDateAtt(sendData);
    }

    public int getTotalCountByDateAtt(String selectedDay) {
        return am.getTotalCountByDateAtt(selectedDay);
    }
}
