package com.ohgiraffers.refactorial.mail.service;

import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.model.dao.MailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    private MailDAO mailDAO;

    @Autowired
    public MailService(MailDAO mailDAO) {
        this.mailDAO = mailDAO;
    }

    // 메일 보내기
    public void sendMail(MailDTO mailDTO) {
        mailDAO.sendMail(mailDTO);
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
