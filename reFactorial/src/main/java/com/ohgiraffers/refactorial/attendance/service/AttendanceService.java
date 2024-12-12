package com.ohgiraffers.refactorial.attendance.service;

import com.ohgiraffers.refactorial.attendance.dao.AttendanceMapper;
import com.ohgiraffers.refactorial.attendance.dto.AttendanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Transactional
    public void addEmpAttendance(AttendanceDTO attendance) {

        // 6시 이전(즉, 오전12시부터 새벽 시간인지 판별)
        boolean existWorkTime = attendance.getAttTime().isBefore(LocalTime.of(6, 0));
        if (existWorkTime) {
            System.out.println("근무 시간 외입니다.");
            return;
        }

        Map<String, Object> searchData = new HashMap<>();
        searchData.put("empId",attendance.getEmpId());
        searchData.put("attDate",attendance.getAttDate());

        // 중복 근태 확인
        AttendanceDTO exist = attendanceMapper.checkAttendance(searchData);
        if (exist != null) {
            System.out.println("이미 근태 기록이 있습니다.");
            return;
        }

        int result = attendanceMapper.addEmpAttendance(attendance);
    }

    public AttendanceDTO getAttendance(String userId) {

        LocalDate today = LocalDate.now();

        Map<String, Object> searchData = new HashMap<>();

        searchData.put("empId",userId);
        searchData.put("attDate",today);

        AttendanceDTO attendance = attendanceMapper.checkAttendance(searchData);

        return attendance;
    }
}
