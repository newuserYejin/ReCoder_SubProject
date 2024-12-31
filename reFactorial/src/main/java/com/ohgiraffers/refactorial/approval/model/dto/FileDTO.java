package com.ohgiraffers.refactorial.approval.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileDTO {

    private int fileId;          // 파일 ID
    private String pmId;         // 결재 문서 ID
    private String fileName;     // 파일 이름
    private String filePath;     // 파일 경로
    private long fileSize;       // 파일 크기
    private String fileType;     // 파일 유형 (예: image/jpeg)
    private LocalDateTime uploadedAt; // 업로드 시간
}
