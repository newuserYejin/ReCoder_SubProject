package com.ohgiraffers.refactorial.mail.controller;

import com.ohgiraffers.refactorial.mail.model.dto.FileDTO;
import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.service.MailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    // 메일 쓰기 페이지로 이동
    @GetMapping("/sendMail")
    public String showSendMailPage() {
        return "/mail/sendMail";
    }

    // 메일 보내기
    @PostMapping("/sendMail")
    public String sendMail(@ModelAttribute MailDTO mailDTO,
                           HttpSession session) throws IOException {

        System.out.println(mailDTO);
//        System.out.println(emailAttachments);
        System.out.println(session);

        String senderEmpId = (String) session.getAttribute("LoginUserInfo");
        mailDTO.setSenderEmpId(senderEmpId);

        // 해야할 일
        // 1. 값불러와지는것확인함
        // 2. 첨부파일을 어떤 식으로 서버에 저장할지 고민
        // 3. 첨부파일이 mailDTO 에 emailAttachment 라는 변수에 저장됨
        // 그러면 이메일어테치먼트를 꺼내서 로컬저장위치로 변경
        // 4. 저장위치를 서비스에 보내서 db에 저장

        // 다중 파일 처리
//        List<FileDTO> fileDTOList = handleFileUpload(emailAttachments);
//        mailDTO.setEmailAttachment(fileDTOList);

//        mailService.sendMail(mailDTO);
        return "redirect:/mail/sentMails";
    }

    private List<FileDTO> handleFileUpload(List<MultipartFile> emailAttachments) throws IOException {
        // 업로드된 파일을 저장할 경로
        String filePath = "src/main/resources/static/mail-attachments";

        // 저장 폴더 생성
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 파일 정보 리스트
        List<FileDTO> fileDTOList = new ArrayList<>();

        for (MultipartFile file : emailAttachments) {
            if (!file.isEmpty()) {
                String originalFileName = file.getOriginalFilename();
                String ext = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
                String savedFileName = UUID.randomUUID().toString().replace("-", "") + ext;

                file.transferTo(new File(filePath + "/" + savedFileName));

                FileDTO fileDTO = new FileDTO();
                fileDTO.setOriginalFileName(originalFileName);
                fileDTO.setSavedFileName(savedFileName);
                fileDTO.setFilePath(filePath);
                fileDTO.setDescription("Attachment for mail");

                fileDTOList.add(fileDTO);
            }
        }

        return fileDTOList;
    }

//    // 내가 보낸 메일 조회
//    @GetMapping("/sentMails")
//    public String getSentMails(HttpSession session, Model model) {
//        String senderEmpId = (String) session.getAttribute("LoginUserInfo");
//        List<MailDTO> sentMails = mailService.getSentMails(senderEmpId);
//        model.addAttribute("sentMails", sentMails);
//        return "/mail/sentMails";
//    }
//
//    // 내가 받은 메일 조회
//    @GetMapping("/receivedMails")
//    public String getReceivedMails(HttpSession session, Model model) {
//        String receiverEmpId = (String) session.getAttribute("LoginUserInfo");
//        List<MailDTO> receivedMails = mailService.getReceivedMails(receiverEmpId);
//        model.addAttribute("receivedMails", receivedMails);
//        return "/mail/receivedMail";
//    }
//
//    // 답장하기
//    @GetMapping("/replyMail/{emailId}")
//    public String replyMail(@PathVariable String emailId, Model model) {
//        MailDTO mailDTO = mailService.getMailById(emailId);
//        model.addAttribute("mailDTO", mailDTO);
//        return "/mail/sendMail";
//    }
//
//    // 메일 휴지통
//    @PostMapping("/deleteMail/{emailId}")
//    public String deleteMail(@PathVariable String emailId) {
//        mailService.deleteMail(emailId);
//        return "redirect:/mail/receivedMails";
//    }
}
