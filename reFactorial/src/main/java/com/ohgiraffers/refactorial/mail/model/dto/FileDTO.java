package com.ohgiraffers.refactorial.mail.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FileDTO {
    private int id;
    private String originalFileName;
    private String savedFileName;
    private String filePath;
    private String description;
    private String emailId;
}