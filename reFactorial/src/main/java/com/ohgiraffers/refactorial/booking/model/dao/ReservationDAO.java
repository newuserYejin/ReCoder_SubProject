package com.ohgiraffers.refactorial.booking.model.dao;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ReservationDAO {

    // 모든 회의실 번호를 가져오는 메서드
    List<BigDecimal> getAllRooms();

    // 예약 정보를 데이터베이스에 저장하는 메서드
    void insertReservation(ReservationDTO reservationDTO);

}
