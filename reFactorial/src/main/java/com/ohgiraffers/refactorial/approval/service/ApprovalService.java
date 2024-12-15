package com.ohgiraffers.refactorial.approval.service;

import com.ohgiraffers.refactorial.approval.model.dao.ApprovalMapper;
import com.ohgiraffers.refactorial.approval.model.dao.EmployeeMapper;
import com.ohgiraffers.refactorial.approval.model.dto.ApprovalRequestDTO;
import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApprovalService {


    @Autowired
    private ApprovalMapper approvalMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

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


    public String saveApproval(ApprovalRequestDTO approvalRequestDTO, String creatorId) {

        Map<String, Object> params = new HashMap<>();

        //5자리 랜덤 문자열 생성
        String pmId = "PM" + String.format("%03d", (int) (Math.random() * 1000));

        params.put("pmId", pmId);
        params.put("title", approvalRequestDTO.getTitle());
        params.put("date", LocalDateTime.now()); // 현재 날짜
        params.put("category", approvalRequestDTO.getCategory());
        params.put("attachment", approvalRequestDTO.getAttachment());
        params.put("creatorId", creatorId); // 작성자 ID 추가
        params.put("content", approvalRequestDTO.getContent()); // 추가된 내용

        approvalMapper.insertPm(params); // 매퍼 호출
        return String.valueOf(params.get("pmId")); // 생성된 pmId 반환
    }


    public void saveApprovers(String pmId, List<String> approvers) {
        // 중복 제거
        List<String> uniqueApprovers = new ArrayList<>(new HashSet<>(approvers));

        if (!uniqueApprovers.isEmpty()) {
            Map<String, Object> params = new HashMap<>();
            params.put("pmId", pmId);
            params.put("approvers", uniqueApprovers);

            approvalMapper.saveApprovers(params);
        }
    }


    // 참조자 저장
    public void saveReferrers(String pmId, List<String> referrers) {
        approvalMapper.saveReferrers(pmId, referrers);
    }


    public List<DocumentDTO> getWaitingDocuments(String empId, int limit, int offset) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("empId는 null 또는 비어있을 수 없습니다.");
        }


        // 참조자 문서 조회
        Map<String, Object> params = new HashMap<>();
        params.put("empId", empId);
        params.put("limit", limit);
        params.put("offset", offset);

        return approvalMapper.getWaitingDocuments(empId);
    }

    public int getWaitingCount(String empId) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("empId는 null 또는 비어있을 수 없습니다.");
        }

        // 작성자가 작성한 문서의 총 개수 조회
        return approvalMapper.getWaitingCount(empId);

    }


    public String findEmpIdByName(String name) {
        return userMapper.findEmpIdByName(name); // 이름으로 emp_id 조회
    }

    public List<DocumentDTO> getReferenceDocuments(String empId, int limit, int offset) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("empId는 null 또는 비어있을 수 없습니다.");
        }

        // 참조자 문서 조회
        Map<String, Object> params = new HashMap<>();
        params.put("empId", empId);
        params.put("limit", limit);
        params.put("offset", offset);

        return approvalMapper.getReferenceDocuments(params);
    }

    public int getTotalReferenceDocuments(String empId) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("empId는 null 또는 비어있을 수 없습니다.");
        }
        return approvalMapper.getTotalReferenceDocuments(empId);
    }


    public List<DocumentDTO> getMyDocuments(String empId, int limit, int offset) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("empId는 null 또는 비어있을 수 없습니다.");
        }

        // 파라미터를 Map으로 묶어 매퍼에 전달
        Map<String, Object> params = new HashMap<>();
        params.put("empId", empId);
        params.put("limit", limit);
        params.put("offset", offset);

        // 작성자가 작성한 문서 가져오기 (LIMIT과 OFFSET을 적용한 쿼리 호출)
        List<DocumentDTO> myDocuments = approvalMapper.getMyDocuments(params);


        return myDocuments;  // 결과 반환
    }


    public int getMyDocumentsCount(String empId) {
        if (empId == null || empId.isEmpty()) {
            throw new IllegalArgumentException("empId는 null 또는 비어있을 수 없습니다.");
        }

        // 작성자가 작성한 문서의 총 개수 조회
        return approvalMapper.getMyDocumentsCount(empId);
    }


    public void saveApprovalFile(String pmId, String fileName, String filePath, long fileSize, String fileType) {
        // 파일 정보 저장을 위한 로직
        Map<String, Object> fileInfo = new HashMap<>();
        fileInfo.put("pmId", pmId);
        fileInfo.put("fileName", fileName);
        fileInfo.put("filePath", filePath);
        fileInfo.put("fileSize", fileSize);
        fileInfo.put("fileType", fileType);

        // DB에 파일 정보 저장
        approvalMapper.insertApprovalFile(fileInfo);
    }


    public DocumentDTO getDocumentById(String pmId) {
        return  approvalMapper.getDocumentById(pmId);
    }
}


