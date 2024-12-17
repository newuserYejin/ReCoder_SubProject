package com.ohgiraffers.refactorial.mail.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailDTO {
    private String emailId;

    private String emailTitle;

    private String emailContent;

    private MultipartFile emailAttachment;

    private boolean readStatus;

    private Date sentDate;

    private boolean trashStatus;

    private String senderEmpId;

    private String receiverEmpId;
}

