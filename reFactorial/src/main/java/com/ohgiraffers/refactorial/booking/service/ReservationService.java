package com.ohgiraffers.refactorial.booking.service;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.model.dao.ReservationDAO;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public void createReservation(ReservationDTO reservationDTO) {
        reservationDAO.insertReservation(reservationDTO);
    }
}
