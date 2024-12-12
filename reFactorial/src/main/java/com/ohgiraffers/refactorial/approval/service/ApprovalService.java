package com.ohgiraffers.refactorial.approval.service;

import com.ohgiraffers.refactorial.approval.model.dao.ApprovalMapper;
import com.ohgiraffers.refactorial.approval.model.dao.EmployeeMapper;
import com.ohgiraffers.refactorial.approval.model.dto.ApprovalRequestDTO;
import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ApprovalService {


    @Autowired
    private ApprovalMapper approvalMapper;

    @Autowired
    private EmployeeMapper employeeMapper;


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


    public String saveApproval(ApprovalRequestDTO approvalRequestDTO) {
        String pmId = UUID.randomUUID().toString().substring(0, 5);  // 고유문자열생성(pk), 36자리 고유 문자열을 생성하고, 이를 잘라서 5자리만 사용함

        approvalMapper.insertPm(
                pmId,
                approvalRequestDTO.getTitle(),
                approvalRequestDTO.getCategory(),
                new Date(),
                approvalRequestDTO.getAttachment()



        );
        return pmId;
    }


    public void saveApprovers(String pmId, List<String> approvers) {
        for (String approver : approvers) {
            approvalMapper.insertApprover(approver, pmId, false); // false = 승인상태를 나타냄 아직 승인하지않은상태
        }
    }

    public void saveReferrers(String pmId, List<String> referrers) {
        for (String referrer : referrers) {
            approvalMapper.insertReferrer(pmId, referrer);
        }

    }

    public List<DocumentDTO> getDocumentsForProcessing() {

        return approvalMapper.getDocuments(); // JOIN 데이터를 가져옴
    }

    public List<DocumentDTO> getWaitingDocuments() {
        return approvalMapper.getWaitingDocuments();
    }


}




//    public List<DocumentDTO> getReferenceDocuments() {
//
//        return approvalMapper.getReferenceDocuments();
//    }
