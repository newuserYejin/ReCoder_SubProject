package com.ohgiraffers.refactorial.booking.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {

    private String reservationId;      // reservation_id (varchar)
    private Date reservationDate;      // reservation_date (date)
    private String empId;              // emp_id (varchar)
    private BigDecimal conferenceRoomNo; // conferenceRoom_no (decimal)

}
