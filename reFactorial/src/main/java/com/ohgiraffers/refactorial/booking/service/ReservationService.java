package com.ohgiraffers.refactorial.booking.service;

import com.ohgiraffers.refactorial.booking.model.dao.ReservationMapper;
import com.ohgiraffers.refactorial.booking.model.dto.CombineDTO;
import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {

    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationService(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    // 회의실 별 날짜별 예약 내역 가져오기
    public List<ReservationDTO> getReserveByRoomNo(String selectedDate, String roomNo) {
        Map<String, Object> sendData = new HashMap<>();

        sendData.put("selectedDate",selectedDate);
        sendData.put("roomNo",roomNo);

        return reservationMapper.getReserveByRoomNo(sendData);
    }

    // 회의실 예약을 처리하는 메서드
    public int addReserveRoom(ReservationDTO reservation) {
        return reservationMapper.addReservation(reservation);
    }

    // 개인페이지 예약 내역 메서드
    public List<CombineDTO> getUserReservations(String empId) {
        return reservationMapper.getUserReservations(empId);
    }

    // 예약 삭제 메서드
    public int deleteReservationById(String reservationId) {
        return reservationMapper.deleteReservationById(reservationId);
    }
}
