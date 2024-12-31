package com.ohgiraffers.refactorial.fileUploade.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadFileDTO {

    private int fileId;          // 파일 ID
    private String mappingId;         // 결재 문서 ID
    private String storeFileName;  // 파일 이름
    private String originFileName; //원래(업로드 될때)의 파일 이름
    private long fileSize;       // 파일 크기
    private String fileType;     // 파일 유형 (예: image/jpeg)
    private LocalDateTime uploadedAt; // 업로드 시간

}
