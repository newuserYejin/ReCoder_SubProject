package com.ohgiraffers.refactorial.approval.model.dao;

import com.ohgiraffers.refactorial.approval.model.dto.ApprovalRequestDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface EmployeeMapper {



    List<EmployeeDTO> searchByName(String name);

    List<EmployeeDTO> findAllEmployees();

    List<EmployeeDTO> searchByReferrersPageName(String name);

    List<EmployeeDTO> findAllReferrers();




    // 이름으로 emp_id를 조회
    String findEmpIdByName(@Param("name") String name);

    // emp_id로 이름을 조회
    String findNameByEmpId(@Param("empId") String empId);

    // 연차,반차
    void updateLeaveBalances(String empId, BigDecimal deduction);
}

//    Object findNameByEmpId(String id);

