package com.ohgiraffers.refactorial.sharedWork.model.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SharedWorkDTO {

    private String workId;              // 업무고유 ID
    private String workTitle;           // 업무 제목
    private String workExplanation;     // 업무 설명
    private LocalDate deadLine;     // 마감기한
    private LocalDate workSchedule; // 일정날짜
    private int deptCode;            // 부서코드
    private String workColor;        // 일정 색상

}