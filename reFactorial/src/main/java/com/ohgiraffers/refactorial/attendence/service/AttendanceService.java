package com.ohgiraffers.refactorial.attendence.service;

import com.ohgiraffers.refactorial.attendence.dao.AttendanceMapper;
import com.ohgiraffers.refactorial.user.model.dto.AttendanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

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

        // 중복 근태 확인
        int exist = attendanceMapper.checkAttendance(attendance);
        if (exist > 0) {
            System.out.println("이미 근태 기록이 있습니다.");
            return;
        }

        int result = attendanceMapper.addEmpAttendance(attendance);

//        if (result > 0){
//            System.out.println(attendance.getEmpId()+"님의 근태를 추가 했습니다.");
//        } else {
//            System.out.println(attendance.getEmpId()+"님의 근태를 추가 실패했습니다.");
//        }
    }

}
