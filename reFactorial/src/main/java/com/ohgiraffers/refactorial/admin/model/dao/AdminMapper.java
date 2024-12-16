package com.ohgiraffers.refactorial.admin.model.dao;

import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<LoginUserDTO> getAllEmployee();

    Integer modifyEmpInfoUpdate(UserDTO user);
}
