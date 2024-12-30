package com.ohgiraffers.refactorial.user.model.dao;

import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    LoginUserDTO findByUsername(String username);

    int addEmployee(UserDTO userDTO);

    String findDeptName(int deptCode);

    String findPositionName(int positionValue);

    int changePW(Map<String, String> updateData);

    int updatePersonalInfo(Map<String, String> updateData);

    String findEmpIdByName(String name);

    String getNameById(String empId);

    List<Map<String, Object>> getHiredDateGroupBy();

    int addCheckEvent(Map<String, Object> sendData);

    int getCheckEvent(Map<String, Object> sendData);

    List<String> getAllCheckEvent(Map<String, Object> sendData);
}
