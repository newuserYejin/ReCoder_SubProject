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


    void insertReferrer(@Param("pmId") String pmId, @Param("empId") String empId);


    List<DocumentDTO> getDocuments();

    List<DocumentDTO> getWaitingDocuments();


    void saveApprovers(Map<String, Object> params);
}
