package com.ohgiraffers.refactorial.addressBook.model.dao;

import com.ohgiraffers.refactorial.addressBook.model.dto.FactoryDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    // 주소록 사원검색
    List<EmployeeDTO> findAllEmployees();
    List<EmployeeDTO> findEmployeesByKeyword(String keyword);
    String findDeptName(int deptCode);
    String findPositionName(int positionValue);

    //사원검색 페이지네이션
    int getTotalEmployeeCount();
    List<EmployeeDTO> findEmployeesWithPagination(int limit, int offset);

    // 주소록 협력업체
    List<FactoryDTO> findAllFactories();
    List<FactoryDTO> searchFactoriesByKeyword(String keyword);
}
