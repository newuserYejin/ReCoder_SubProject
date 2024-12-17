package com.ohgiraffers.refactorial.booking.service;

import com.ohgiraffers.refactorial.booking.model.dao.CabinetDAO;
import com.ohgiraffers.refactorial.booking.model.dto.CabinetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CabinetService {

    private final CabinetDAO cabinetDAO;

    @Autowired
    public CabinetService(CabinetDAO cabinetDAO) {
        this.cabinetDAO = cabinetDAO;
    }

    // 모든 회의실 목록을 가져오는 메서드 추가
    public List<CabinetDTO> getAllCabinets() {
        return cabinetDAO.getAllCabinets();
    }
}
