package com.ohgiraffers.refactorial.mail.service;

import com.ohgiraffers.refactorial.mail.model.dao.MailDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final MailDAO mailDAO;

    @Autowired
    public MailService(MailDAO mailDAO) {
        this.mailDAO=mailDAO;
    }
}
