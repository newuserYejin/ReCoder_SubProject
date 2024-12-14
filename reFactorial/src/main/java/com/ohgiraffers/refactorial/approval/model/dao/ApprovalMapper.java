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


//    void insertReferrer(@Param("pmId") String pmId, @Param("empId") String empId);


    List<DocumentDTO> getWaitingDocuments(String empId);


    void saveApprovers(Map<String, Object> params);


    List<DocumentDTO> getReferenceDocuments(@Param("empId") String empId);



    void saveReferrers(String pmId, List<String> referrers);



    List<DocumentDTO> getMyDocuments(Map<String, Object> params);

    int getMyDocumentsCount(String empId);
}
