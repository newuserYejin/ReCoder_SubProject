package com.ohgiraffers.refactorial.inquiry.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InquiryDTO {
    private String iqrValue;      // 문의 고유 ID
    private String iqrTitle;      // 문의 제목
    private String iqrDetails;    // 문의 내용
    private boolean answerStatus;  // 답변 상태
    private LocalDateTime iqrCreationDate; // 문의 생성 날짜 및 시간
    private String empId;         // 문의자 사원번호
    private String answerDetail;   // 답변 내용 (NULL 허용)
}
