package com.ohgiraffers.refactorial.booking.service;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.model.dao.ReservationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // 모든 예약 목록을 가져오는 메서드 추가
    public List<ReservationDTO> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    // 회의실 예약을 처리하는 메서드
    public void reserveConferenceRoom(ReservationDTO reservationDTO) {
        reservationDAO.insertReservation(reservationDTO);
    }

    // 예약 삭제 메서드
    public void deleteReservationById(String reservationId) {
        reservationDAO.deleteReservationById(reservationId);
    }

    public boolean isReservationAvailable(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<ReservationDTO> conflicts = reservationDAO.checkReservationConflict(date, startTime, endTime);
        return conflicts.isEmpty(); // 중복 예약이 없으면 true 반환
    }
}
