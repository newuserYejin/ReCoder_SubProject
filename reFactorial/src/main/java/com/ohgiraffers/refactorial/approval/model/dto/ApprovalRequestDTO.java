package com.ohgiraffers.refactorial.approval.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalRequestDTO {

    private String title; // 결재 제목
    private String category; // 문서 분류
    private String attachment; // 첨부 파일 경로
    private List<String> approvers; // 승인자 리스트
    private List<String> referrers; // 참조자 리스트
}
