package com.ohgiraffers.refactorial.mail.controller;

import com.ohgiraffers.refactorial.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {

    private MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/mail/writeMail")
    public String mailWritePage() {
        return "mail/writeMail"; // templates/mail/writeMail.html
    }

}
