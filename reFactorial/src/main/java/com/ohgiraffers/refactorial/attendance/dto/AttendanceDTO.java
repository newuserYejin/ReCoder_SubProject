package com.ohgiraffers.refactorial.attendance.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttendanceDTO {

    private LocalDate attDate;
    private LocalTime attTime;
    private String attStatus;
    private String empId;

}
