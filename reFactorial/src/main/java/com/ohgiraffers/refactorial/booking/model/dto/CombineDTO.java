package com.ohgiraffers.refactorial.booking.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CombineDTO {

    private CabinetDTO cabinetDTO;
    private ReservationDTO reservationDTO;

}
