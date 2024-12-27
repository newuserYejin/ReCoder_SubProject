package com.ohgiraffers.refactorial.sharedWork.model.dao;

import com.ohgiraffers.refactorial.sharedWork.model.dto.SharedWorkDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SharedWorkMapper {

    // 업무 전체 조회
    List<SharedWorkDTO> getAllSharedWork(@Param("deptCode") int deptCode);
    // 업무 저장
    void saveSharedWork(SharedWorkDTO sharedWork);
    // 일정 삭제
    int deleteEventById(String workId);
    // 일정 조회
    SharedWorkDTO findEventById(String workId);
    // 일정 수정
    int updateSharedWork(SharedWorkDTO updatedWork);
}
