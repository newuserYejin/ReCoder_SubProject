package com.ohgiraffers.refactorial.user.model.dao;

import com.ohgiraffers.refactorial.user.model.dto.AttendanceDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {

    UserDTO findByUsername(String username);

    int addEmployee(UserDTO userDTO);

    String findDeptName(int deptCode);

    String findPositionName(int positionValue);

    int changePW(Map<String, String> updateData);

    int updatePersonalInfo(Map<String, String> updateData);

}
