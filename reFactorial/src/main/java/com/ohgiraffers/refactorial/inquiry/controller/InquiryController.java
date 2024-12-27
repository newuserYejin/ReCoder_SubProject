package com.ohgiraffers.refactorial.inquiry.controller;

import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import com.ohgiraffers.refactorial.inquiry.service.InquiryService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String inquiry(Model model, HttpSession session) {
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        if (loginUser == null) {
            throw new IllegalArgumentException("로그인 정보가 없습니다.");
        }

        String senderEmpId = loginUser.getEmpId();
        List<InquiryDTO> sentInquiries = inquiryService.sentInquiries(senderEmpId);

        System.out.println("Sent Inquiries: " + sentInquiries);

        model.addAttribute("sentInquiries", sentInquiries);
        return "/inquiry/inquiryList";
    }
}
