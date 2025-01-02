package com.ohgiraffers.refactorial.addressBook.service;


import com.ohgiraffers.refactorial.addressBook.model.dao.AddressBookMapper;
import com.ohgiraffers.refactorial.addressBook.model.dto.FactoryDTO;
import com.ohgiraffers.refactorial.approval.model.dao.EmployeeMapper;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        return employees;
    }

    public List<EmployeeDTO> searchEmployees(String keyword) {
        List<EmployeeDTO> employees = addressBookMapper.findEmployeesByKeyword(keyword);
        List<EmployeeDTO> filteredEmployees = new ArrayList<>();

        for (EmployeeDTO employee : employees) {
            if (!"ACCESSLIMIT".equals(employee.getViewAuth())) {
                String deptName = addressBookMapper.findDeptName(employee.getDeptCode());
                String positionName = addressBookMapper.findPositionName(employee.getPositionValue());
                employee.setDeptName(deptName);
                employee.setPositionName(positionName);
                filteredEmployees.add(employee);
            }
        }
        return filteredEmployees;
    }
    public int getEmployeeCount() {
        return addressBookMapper.getTotalEmployeeCount();
    }

    public List<EmployeeDTO> getEmployeesByPage(int limit, int offset) {
        return addressBookMapper.findEmployeesWithPagination(limit, offset);
    }


    // 전체 협력업체 조회
    public List<FactoryDTO> getAllFactories() {
        return addressBookMapper.findAllFactories();
    }

    // 키워드로 협력업체 검색
    public List<FactoryDTO> searchFactories(String keyword) {
        return addressBookMapper.searchFactoriesByKeyword(keyword);
    }
}
