package com.ohgiraffers.refactorial.booking.model.dao;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface ReservationDAO {

    // 모든 예약을 가져오는 메서드
    List<ReservationDTO> getAllReservations(); // 예약 정보 DTO를 반환하는 메서드 추가

    // 예약 정보를 데이터베이스에 저장하는 메서드
    void insertReservation(ReservationDTO reservationDTO);

    // 예약을 삭제하는 메서드 (reservationId 기준)
    void deleteReservationById(String reservationId);

    // 중복을 방지하는 메서드
    List<ReservationDTO> checkReservationConflict(LocalDate date, LocalTime startTime, LocalTime endTime);

}