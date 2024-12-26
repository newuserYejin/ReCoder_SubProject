package com.ohgiraffers.refactorial.sharedWork.service;

import com.ohgiraffers.refactorial.sharedWork.model.dao.SharedWorkMapper;
import com.ohgiraffers.refactorial.sharedWork.model.dto.SharedWorkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedWorkService {

    private final SharedWorkMapper sharedWorkMapper;

    @Autowired
    public SharedWorkService(SharedWorkMapper sharedWorkMapper) {
        this.sharedWorkMapper = sharedWorkMapper;
    }

    // 업무 전체 조회
    public List<SharedWorkDTO> getAllSharedWork(int deptCode) {
        return sharedWorkMapper.getAllSharedWork(deptCode);
    }


    // 업무 저장
    public void saveSharedWork(SharedWorkDTO sharedWork) {

        System.out.println("sharedWork 서비스 = " + sharedWork);

        sharedWorkMapper.saveSharedWork(sharedWork);
    }


}
