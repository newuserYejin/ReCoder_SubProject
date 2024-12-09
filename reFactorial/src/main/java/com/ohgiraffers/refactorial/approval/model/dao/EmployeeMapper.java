package com.ohgiraffers.refactorial.approval.model.dao;

import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<EmployeeDTO> searchEmployee(String name);

    List<EmployeeDTO> searchByName(String name);

    List<EmployeeDTO> findAllEmployees();
}
