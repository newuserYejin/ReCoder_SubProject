package com.ohgiraffers.refactorial.mail.model.dao;

import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailMapper {

    // 메일쓰기
    void sendMail(MailDTO mailDTO);

    // 내가 보낸 메일 읽기
    List<MailDTO> getSentMails(String senderEmpId);

    // 내가 받은 메일 읽기
    List<MailDTO> getReceivedMails(String receiverEmpId);

}
