package com.ohgiraffers.refactorial.sharedWork.service;

import com.ohgiraffers.refactorial.sharedWork.model.dao.SharedWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SharedWorkService {

    private final SharedWorkMapper sharedWorkMapper;

    @Autowired
    public SharedWorkService(SharedWorkMapper sharedWorkMapper) {
        this.sharedWorkMapper = sharedWorkMapper;
    }


}
