package com.ohgiraffers.refactorial.inquiry.controller;

import com.ohgiraffers.refactorial.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    private InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    // 문의 작성 페이지로 이동
    @GetMapping("/sendInquiry")
    public String showInquiryPage() {
        return "inquiry/sendInquiry";
    }

    // 1:1 문의 내역 페이지로 이동
    @GetMapping("/inquiryList")
    public String inquiry() {
        return "/inquiry/inquiryList";
    }
}
