package com.ohgiraffers.refactorial.mail.controller;

import com.ohgiraffers.refactorial.fileUploade.model.dto.UploadFileDTO;
import com.ohgiraffers.refactorial.fileUploade.model.service.UploadFileService;
import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.service.MailService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/mail")
public class MailController {

    private MailService mailService;
    private UploadFileService uploadService;

    @Autowired
    public MailController(MailService mailService, UploadFileService uploadService) {
        this.uploadService = uploadService;
        this.mailService = mailService;
    }

    // 메일 쓰기 페이지로 이동
    @GetMapping("/sendMail")
    public String showSendMailPage() {

        return "/mail/sendMail";
    }

    // 메일 보내기
    @PostMapping("/sendMail")
    public String sendMail(@ModelAttribute MailDTO mailDTO, HttpSession session, Model model,@RequestParam(required = false) List<MultipartFile> mailFileList) throws IOException {
        // 로그인 유저 가져오기
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        // 발신자 정보 설정
        String senderEmpId = loginUser.getEmpId();
        mailDTO.setSenderEmpId(senderEmpId);  // 발신자 정보 설정

        // 수신자 정보가 없을 경우 예외 처리 또는 안내
        if (mailDTO.getReceiverEmpIds() == null || mailDTO.getReceiverEmpIds().isEmpty()) {
            model.addAttribute("error", "수신자를 선택해주세요.");
            return "mail/sendMail"; // 다시 메일 보내기 페이지로 이동
        }

        // ID 생성 및 중복 방지 로직
        Set<String> generatedIds = new HashSet<>();
        String emId;
        do {
            emId = "EM" + String.format("%05d", (int) (Math.random() * 100000));
        } while (!generatedIds.add(emId)); // 중복이 아니면 Set에 추가

        if (!mailFileList.isEmpty()) {
            mailDTO.setMailfile(mailFileList);
            mailDTO.setAttachment(1);

            uploadService.upLoadFile(mailFileList,emId);
        }

        // 메일 서비스 호출
        mailService.sendMail(mailDTO);

        // 리디렉션 후 메일 보내기 화면으로 이동
        return "redirect:/mail/sendMail";
    }

    //내가 보낸 메일 읽기
    @GetMapping("/sentMails")
    public String sentMails(Model model, HttpSession session) {
        // 로그인 유저 정보 가져오기
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        String senderEmpId = loginUser.getEmpId();

        // 보낸 메일 목록을 모델에 추가
        List<MailDTO> sentMails = mailService.getSentMails(senderEmpId);

        // Model sentMails 데이터가 제대로 추가되었는지 확인
        model.addAttribute("sentMails", sentMails);

        return "/mail/sentMails";
    }

    //내가 받은 메일 읽기
    @GetMapping("/receivedMails")
    public String receivedMails(Model model, HttpSession session) {
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        String receiverEmpIds = loginUser.getEmpId();

        // 내가 받은 메일 목록을 모델에 추가
        List<MailDTO> receivedMails = mailService.getReceivedMails(receiverEmpIds);

        // Model receivedMails 데이터가 제대로 추가되었는지 확인
        model.addAttribute("receivedMails", receivedMails);

        return "/mail/receivedMails";
    }

    // 메일 상세 페이지
    @GetMapping("/detail")
    public String mailDetail(@RequestParam("emailId") String emailId, Model model) {
        MailDTO mailDetail = mailService.getMailDetail(emailId);
        List<String> mailReceiver = mailService.getReceiverEmpIds(emailId);

        if (mailDetail.getAttachment() == 1){
            List<UploadFileDTO> uploadFileList = uploadService.findFileByMappingId(emailId);

            if (!uploadFileList.isEmpty()){
                model.addAttribute("attachmentFileList",uploadFileList);
            }
        }

        model.addAttribute("mailDetail", mailDetail);
        model.addAttribute("mailReceiver", mailReceiver);
        return "/mail/mailDetail";
    }

    // 메일 휴지통 페이지
    @GetMapping("/detailBin")
    public String mailDetailBin(@RequestParam("emailId") String emailId, Model model) {
        MailDTO mailDetailBin = mailService.getMailDetailBin(emailId);
        model.addAttribute("mailDetailBin", mailDetailBin);
        return "/mail/mailDetailBin";
    }

    // 답신 페이지로 이동
    @GetMapping("/reply")
    public String showReplyPage(@RequestParam("emailId") String emailId, Model model) {
        MailDTO originalMail = mailService.getMailDetail(emailId);

        if (originalMail == null) {
            throw new IllegalArgumentException("해당 이메일이 존재하지 않습니다.");
        }

        model.addAttribute("originalMail", originalMail);
        return "/mail/replyMail"; // 템플릿 경로 확인
    }

    // 메일 답신
    @PostMapping("/reply")
    public String replyMail(@ModelAttribute MailDTO mailDTO, HttpSession session) {
        // 로그인 유저 가져오기
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        // 발신자 정보 설정 (로그인한 사용자)
        mailDTO.setSenderEmpId(loginUser.getEmpId());

        // 수신자 설정 (원본 메일의 발신자가 수신자가 됨)
        String receiverEmpId = mailDTO.getReceiverEmpIds().get(0); // 원본 메일의 발신자를 수신자로 설정
        mailDTO.setReceiverEmpIds(Arrays.asList(receiverEmpId));

        // 메일 서비스 호출 (답신 메일 보내기)
        mailService.sendMail(mailDTO);

        return "redirect:/mail/sentMails"; // 답신 후 보낸 메일 목록으로 리디렉션
    }

    // 휴지통 보기
    @GetMapping("/mailBin")
    public String viewMailBin(Model model, HttpSession session) {
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        String senderEmpId = loginUser.getEmpId();

        List<MailDTO> sentMailsBin = mailService.getSentMailsBin(senderEmpId);
        model.addAttribute("sentMailsBin", sentMailsBin);

        return "/mail/mailBin";
    }

    // 휴지통으로 보내기
    @PostMapping("/moveToTrash")
    public String moveToTrash(@RequestParam("emailId") String emailId,
                              @RequestParam("receiverEmpId") String receiverEmpId
                              ) {
        // 특정 수신자에 대해 휴지통으로 이동
        if (receiverEmpId != null && !receiverEmpId.isEmpty()) {
            mailService.moveToTrash(emailId, Collections.singletonList(receiverEmpId));
        }
        return "redirect:/mail/mailBin"; // 휴지통 페이지로 리디렉션
    }

    @PostMapping("/removeToTrash")
    public String removeToTrash(@RequestParam("emailId") String emailId) {
        // 메일을 휴지통으로 보내는 서비스 호출
        mailService.removeToTrash(emailId);

        return "redirect:/mail/mailBin"; // 휴지통 페이지로 리디렉션
    }
}
