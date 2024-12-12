package com.ohgiraffers.refactorial.attendence.dao;

import com.ohgiraffers.refactorial.user.model.dto.AttendanceDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttendanceMapper {

    int addEmpAttendance(AttendanceDTO attendance);

    int checkAttendance(AttendanceDTO attendance);

}
