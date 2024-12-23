package com.ohgiraffers.refactorial.booking.service;

import com.ohgiraffers.refactorial.booking.model.dao.ReservationMapper;
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

    // 모든 예약 목록을 가져오는 메서드 추가
    public List<ReservationDTO> getAllReservations() {
        return reservationMapper.getAllReservations();
    }

    // 회의실 예약을 처리하는 메서드
    public int addReserveRoom(ReservationDTO reservation) {
        return reservationMapper.addReservation(reservation);
    }

    // 예약 삭제 메서드
    public void deleteReservationById(String reservationId) {
        reservationMapper.deleteReservationById(reservationId);
    }
    // 중복 방지 메서드
    public boolean isReservationAvailable(BigDecimal conferenceRoomNo, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // 예약이 겹치는지 체크하는 로직
        List<ReservationDTO> conflicts = reservationMapper.checkReservationConflict(conferenceRoomNo, date, startTime, endTime);
        return conflicts.isEmpty();
    }

    // 개인페이지 예약 내역 메서드
    public List<ReservationDTO> getUserReservations(String empId) {
        return reservationMapper.getUserReservations(empId);
    }

    public List<ReservationDTO> getReserveByRoomNo(String selectedDate, String roomNo) {
        Map<String, Object> sendData = new HashMap<>();

        sendData.put("selectedDate",selectedDate);
        sendData.put("roomNo",roomNo);

        return reservationMapper.getReserveByRoomNo(sendData);
    }
}
