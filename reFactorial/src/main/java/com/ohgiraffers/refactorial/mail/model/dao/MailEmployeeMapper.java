package com.ohgiraffers.refactorial.mail.model.dao;

import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.model.dto.MailEmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailEmployeeMapper {
    List<MailEmployeeDTO> searchMailEmployees(String searchReceiverInput);

    List<MailEmployeeDTO> getAllMailEmployees();

    // 보낸 메일 목록 조회
    List<MailDTO> selectSentMails(String senderEmpId);

    // 받은 메일 목록 조회
    List<MailDTO> selectReceivedMails(String receiverEmpId);
}
