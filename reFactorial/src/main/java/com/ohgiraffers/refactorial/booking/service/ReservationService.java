package com.ohgiraffers.refactorial.booking.service;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.model.dao.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    @Autowired
    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    // 모든 회의실 목록을 가져오는 메서드
    public List<BigDecimal> getAllRooms() {
        return reservationDAO.getAllRooms();
    }

}
