package com.ohgiraffers.refactorial.booking.model.dao;

import com.ohgiraffers.refactorial.booking.model.dto.CombineDTO;
import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {

    // 예약 정보를 데이터베이스에 저장하는 메서드
    int addReservation(ReservationDTO reservation);

    // 회의실 별 날짜별 예약 내역 가져오기
    List<ReservationDTO> getReserveByRoomNo(Map<String, Object> sendData);

    // 개인 회의실 예약 내역
    List<CombineDTO> getUserReservations(String empId);

    // 예약을 삭제하는 메서드 (reservationId 기준)
    Integer deleteReservationById(String reservationId);









    // 모든 예약을 가져오는 메서드
    List<ReservationDTO> getAllReservations(); // 예약 정보 DTO를 반환하는 메서드 추가



    // 중복을 방지하는 메서드
    List<ReservationDTO> checkReservationConflict(BigDecimal conferenceRoomNo, LocalDate date, LocalTime startTime, LocalTime endTime);

}