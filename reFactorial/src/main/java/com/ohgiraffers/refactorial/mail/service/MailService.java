package com.ohgiraffers.refactorial.mail.service;

import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.model.dao.MailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private MailDAO mailDAO;

//    // 메일 보내기
//    public void sendMail(MailDTO mailDTO) {
//        mailDAO.sendMail(mailDTO);
//    }
//
//    // 내가 보낸 메일 조회
//    public List<MailDTO> getSentMails(String senderEmpId) {
//        return mailDAO.getSentMails(senderEmpId);
//    }
//
//    // 내가 받은 메일 조회
//    public List<MailDTO> getReceivedMails(String receiverEmpId) {
//        return mailDAO.getReceivedMails(receiverEmpId);
//    }
//
//    // 메일 ID로 메일 조회 (답장하기)
//    public MailDTO getMailById(String emailId) {
//        return mailDAO.getMailById(emailId);
//    }
//
//    // 메일 삭제 (휴지통)
//    public void deleteMail(String emailId) {
//        mailDAO.deleteMail(emailId);
//    }
}
