package com.ohgiraffers.refactorial.booking.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private String reservationId;      // reservation_id (varchar)
    private LocalDate reservationDate; // reservation_date (date)
    private String empId;              // emp_id (varchar)
    private BigDecimal conferenceRoomNo; // ConferenceRoom_no (decimal)
    private LocalTime reservationStartTime; // reservationStartTime (time)
    private LocalTime reservationEndTime;   // reservationEndTime (time)
}