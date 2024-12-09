package com.ohgiraffers.refactorial.booking.model.dao;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationDAO {

    void insertReservation(ReservationDTO reservationDTO);
}
