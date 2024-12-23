package com.ohgiraffers.refactorial.addressBook.model.dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@NoArgsConstructor
public class FactoryDTO {
    private String fabId; // 공장 ID
    private String managerName; // 담당자 이름
    private String managerPhone; // 담당자 전화번호
    private String fabEmail; // 이메일
    private String fabName; // 공장 이름
    private String fabAddress; // 공장 주소
    private String fabProduct; // 생산 제품
    private String empId; // 관리자 번호
    private String fabPhone; // 대표 연락처
}
