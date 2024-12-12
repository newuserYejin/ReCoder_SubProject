package com.ohgiraffers.refactorial.attendance.controller;

import com.ohgiraffers.refactorial.attendance.dto.AttendanceDTO;
import com.ohgiraffers.refactorial.attendance.service.AttendanceService;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/attendance/*")
public class AttendanceController {

    @Autowired
    private AttendanceService as;

    @GetMapping("getAttendance")
    @ResponseBody
    public Map<String, Object> getAttendance(HttpSession session){

        Map<String, Object> res = new HashMap<>();

        UserDTO LoginUserInfo = (UserDTO) session.getAttribute("LoginUserInfo");

        String userId = LoginUserInfo.getEmpId();

        AttendanceDTO attendance = as.getAttendance(userId);

        if (attendance == null){
            res.put("attStatus","근무시간 외");
        } else{
            res.put("attStatus",attendance.getAttStatus());
        }

        return res;
    }


}
