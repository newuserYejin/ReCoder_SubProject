package com.ohgiraffers.refactorial.admin.model.dao;

import com.ohgiraffers.refactorial.attendance.dto.AttendanceDTO;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {
    List<LoginUserDTO> getAllEmployee();

    Integer modifyEmpInfoUpdate(UserDTO user);

    List<AttendanceDTO> getByDateAtt(String selectedDay, int offset, int size);

    int getTotalCountByDateAtt(String selectedDay);

    List<AttendanceDTO> getByDateAtt(Map<String, Object> sendData);
}
