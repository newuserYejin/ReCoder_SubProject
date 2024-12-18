package com.ohgiraffers.refactorial.mail.service;

import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.model.dao.MailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MailService {

    private MailDAO mailDAO;

    @Autowired
    public MailService(MailDAO mailDAO) {
        this.mailDAO = mailDAO;
    }



    // 메일 보내기
    public void sendMail(MailDTO mailDTO) {
        // ID 생성 및 중복 방지 로직
        String emId;
//        do {
//            emId = "EM" + String.format("%05d", (int) (Math.random() * 100000));
//        }
//        while (mailDAO.isEmailIdExists(emId)); // 중복 여부 확인

//        // 공통 메일 ID 설정
//        mailDTO.setEmailId(emId);
//        mailDAO.sendMail(mailDTO);
    }

    // 내가 보낸 메일
    public List<MailDTO> getSentMails(String senderEmpId) {
        List<MailDTO> sentMails = mailDAO.getSentMails(senderEmpId);
        return sentMails;
    }

    public List<MailDTO> getReceivedMails(String receiverEmpId) {
        List<MailDTO> receivedMails = mailDAO.getReceivedMails(receiverEmpId);
        return receivedMails;
    }
}
