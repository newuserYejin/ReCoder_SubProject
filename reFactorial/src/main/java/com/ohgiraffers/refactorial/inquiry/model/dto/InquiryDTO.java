package com.ohgiraffers.refactorial.inquiry.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class InquiryDTO {

    private String iqrValue;       // 문의 고유 ID
    private String iqrSubject;     // 문의 제목
    private String iqrCategory;    // 문의 분류
    private String iqrDetails;     // 문의 내용
    private boolean answerStatus;   // 답변 상태
    private String empId;          // 문의자 사원번호
    private String answerDetail;    // 답변 내용
}
