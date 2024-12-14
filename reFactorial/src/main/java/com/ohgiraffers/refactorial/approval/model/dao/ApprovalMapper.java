package com.ohgiraffers.refactorial.approval.model.dao;

import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApprovalMapper {
    void insertPm(Map<String, Object> params);

    List<DocumentDTO> getWaitingDocuments(String empId);
    int getWaitingCount(String empId);

    void saveApprovers(Map<String, Object> params);
    void saveReferrers(String pmId, List<String> referrers);


    List<DocumentDTO> getReferenceDocuments(Map<String, Object> params);
    int getTotalReferenceDocuments(@Param("empId") String empId);


    List<DocumentDTO> getMyDocuments(Map<String, Object> params);
    int getMyDocumentsCount(String empId);


    void insertApprovalFile(Map<String, Object> params);
}
