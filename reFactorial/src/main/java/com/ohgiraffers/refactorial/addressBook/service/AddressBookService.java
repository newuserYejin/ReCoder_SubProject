package com.ohgiraffers.refactorial.addressBook.service;


import com.ohgiraffers.refactorial.addressBook.model.dao.AddressBookMapper;
import com.ohgiraffers.refactorial.approval.model.dao.EmployeeMapper;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookService {

    @Autowired
    public AddressBookMapper addressBookMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employees = addressBookMapper.findAllEmployees();
        for (EmployeeDTO employee : employees) {
            String deptName = addressBookMapper.findDeptName(employee.getDeptCode());
            String positionName = addressBookMapper.findPositionName(employee.getPositionValue());
            employee.setDeptName(deptName);
            employee.setPositionName(positionName);
        }
        // 디버깅: employees 리스트 출력
        employees.forEach(System.out::println);
        return employees;
    }

    public List<EmployeeDTO> searchEmployees(String keyword) {
        List<EmployeeDTO> employees = addressBookMapper.findEmployeesByKeyword(keyword);
        for (EmployeeDTO employee : employees) {
            String deptName = addressBookMapper.findDeptName(employee.getDeptCode());
            String positionName = addressBookMapper.findPositionName(employee.getPositionValue());
            employee.setDeptName(deptName);
            employee.setPositionName(positionName);
        }
        return employees;
    }
}
