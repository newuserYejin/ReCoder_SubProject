package com.ohgiraffers.refactorial.approval.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DocumentDTO {
        private String pmId;         // 문서 ID
        private String title;        // 제목
        private Date createDate;     // 생성일
        private String category;     // 카테고리
        private String status;       // 상태
        private String attachment;   // 첨부파일 경로
        private String creator;      // 작성자 (사원번호)
        private String approvers;    // 승인자 리스트 (Comma-separated)
        private String referrers;    // 참조자 리스트 (JSON String)


    }






