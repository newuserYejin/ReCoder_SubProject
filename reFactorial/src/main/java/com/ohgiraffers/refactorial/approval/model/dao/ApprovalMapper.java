package com.ohgiraffers.refactorial.approval.model.dao;

import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.model.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApprovalMapper {
    void insertPm(Map<String, Object> params);

    

    int getWaitingCount(String empId);

    void saveApprovers(Map<String, Object> params);
    void saveReferrers(Map<String, Object> params);


    List<DocumentDTO> getReferenceDocuments(Map<String, Object> params);

    int getTotalReferenceDocuments(@Param("empId") String empId);


    List<DocumentDTO> getMyDocuments(Map<String, Object> params);

    int getMyDocumentsCount(String empId);


    void insertApprovalFile(Map<String, Object> params);

    DocumentDTO getDocumentById(String pmId);



    // 파일
    FileDTO findFilesByPmId(String pmId);

    void insertFile(FileDTO file);

    void deleteFileByFileId(int fileId);

    FileDTO findFileByFileId(int fileId);

    FileDTO findFileByFileName(String fileName);

    List<DocumentDTO> getWaitingDocuments(Map<String, Object> params);
}
