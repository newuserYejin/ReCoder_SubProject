package com.ohgiraffers.refactorial.addressBook.model.dao;

import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface AddressBookMapper {


    List<EmployeeDTO> findAllEmployees();
    List<EmployeeDTO> findEmployeesByKeyword(String keyword);
    String findDeptName(int deptCode);
    String findPositionName(int positionValue);

    int getTotalEmployeeCount();

    List<EmployeeDTO> findEmployeesWithPagination(int limit, int offset);
}
