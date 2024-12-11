package com.ohgiraffers.refactorial.approval.service;

import com.ohgiraffers.refactorial.approval.model.dao.EmployeeMapper;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalService {


    private final EmployeeMapper employeeMapper;

    public ApprovalService(EmployeeMapper employeeMapper) {

        this.employeeMapper = employeeMapper;
    }



    public List<EmployeeDTO> searchByName(String name) {

        return employeeMapper.searchByName(name);
    }

    public List<EmployeeDTO> findAllEmployees() {
        return employeeMapper.findAllEmployees();
    }

    public List<EmployeeDTO> searchByReferrersPageName(String name) {
        return employeeMapper.searchByReferrersPageName(name);
    }

    public List<EmployeeDTO> findAllReferrers() {
        return employeeMapper.findAllReferrers();
    }
}
