package com.ohgiraffers.refactorial.attendance.dao;

import com.ohgiraffers.refactorial.attendance.dto.AttendanceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface AttendanceMapper {

    int addEmpAttendance(AttendanceDTO attendance);

    AttendanceDTO checkAttendance(Map<String, Object> searchData);

    List<AttendanceDTO> getAttendance(Map<String, Object> getAttendanceInfo);

    List<Map<String, Object>> getAttendanceGroupBy(LocalDate today);
}
