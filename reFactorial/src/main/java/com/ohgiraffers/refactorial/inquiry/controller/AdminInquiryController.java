package com.ohgiraffers.refactorial.inquiry.controller;

import com.ohgiraffers.refactorial.fileUploade.model.dto.UploadFileDTO;
import com.ohgiraffers.refactorial.fileUploade.model.service.UploadFileService;
import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import com.ohgiraffers.refactorial.inquiry.service.AdminInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminInquiryController {


    private AdminInquiryService adminInquiryService;
    private UploadFileService uploadService;

    @Autowired
    public AdminInquiryController(AdminInquiryService adminInquiryService, UploadFileService uploadService) {
        this.uploadService = uploadService;
        this.adminInquiryService = adminInquiryService;
    }

    // 모든 문의 불러오기
    @GetMapping("/admin/adminInquiryList")
    public String getAllInquiries(Model model) {
        List<InquiryDTO> getAllInquiries = adminInquiryService.getAllInquiries();
        model.addAttribute("getAllInquiries", getAllInquiries);
        return "inquiry/adminInquiryList";
    }

    // 문의 상세 조회
    @GetMapping("/admin/adminInquiryDetail")
    public String adminInquiryDetail( @RequestParam("iqrValue") String iqrValue, Model model) {
        InquiryDTO adminInquiries = adminInquiryService.adminInquiryDetail(iqrValue);

        if (adminInquiries.getAttachment() == 1) {
            List<UploadFileDTO> uploadFileList = uploadService.findFileByMappingId(iqrValue);
            if (!uploadFileList.isEmpty()) {
                model.addAttribute("attachmentFileList", uploadFileList);
            }
        }

        model.addAttribute("adminInquiries", adminInquiries);
        return "inquiry/adminInquiryDetail";
    }

    // 답변 등록
//    @PostMapping("/admin/adminInquiryAnswer")
//    @ResponseBody
//    public String answerInquiry(@RequestParam("iqrValue") String iqrValue, @RequestParam("answerDetail") String answerDetail) {
//        System.out.println("iqrValue: " + iqrValue);
//        System.out.println("answerDetail: " + answerDetail);
//
//        boolean success = adminInquiryService.answerInquiry(iqrValue, answerDetail);
//        System.out.println("답변 등록 성공 여부: " + success); // 추가 로그
//        return success ? "success" : "fail";
//    }

    @PostMapping("/admin/adminInquiryAnswer")
    public ModelAndView answerInquiry(
            @RequestParam("iqrValue") String iqrValue,
            @RequestParam("answerDetail") String answerDetail) {

        boolean success = adminInquiryService.answerInquiry(iqrValue, answerDetail);

        if (success) {
            return new ModelAndView("redirect:/admin/adminInquiryDetail?iqrValue=" + iqrValue);
        } else {
            ModelAndView mav = new ModelAndView("inquiry/adminInquiryDetail");
            mav.addObject("error", "답변 등록에 실패했습니다.");
            return mav;
        }
    }

}
