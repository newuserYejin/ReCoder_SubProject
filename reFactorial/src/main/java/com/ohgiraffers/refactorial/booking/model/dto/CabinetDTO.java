package com.ohgiraffers.refactorial.booking.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CabinetDTO {
    private String roomName;
    private int conferenceRoomNo;
    private String fixtures;
    private String roomLocation;
    private String cabinetImg;
}