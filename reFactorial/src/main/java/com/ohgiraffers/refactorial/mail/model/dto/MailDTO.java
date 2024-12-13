package com.ohgiraffers.refactorial.mail.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailDTO {
    private String emailId;
    private String emailTitle;
    private String emailContent;
    private String emailAttachment;
    private byte readStatus; // tinyint is mapped to byte in Java
    private Date sentDate; // Assuming java.sql.Date
    private byte trashDate; // Since the column type is tinyint
    private String senderEmpId;
    private String receiverEmpId;
}

