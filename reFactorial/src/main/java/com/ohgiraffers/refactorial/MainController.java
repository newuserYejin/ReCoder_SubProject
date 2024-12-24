package com.ohgiraffers.refactorial;

import com.ohgiraffers.refactorial.booking.model.dto.CabinetDTO;
import com.ohgiraffers.refactorial.booking.service.CabinetService;
import com.ohgiraffers.refactorial.booking.service.ReservationService;
import com.ohgiraffers.refactorial.mail.model.dto.MailDTO;
import com.ohgiraffers.refactorial.mail.service.MailService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CabinetService cabinetService;
    @Autowired
    private MailService mailService;


    @GetMapping("/")
    public String defaultURL(Model model){
        return "auth/login";
    }

    @Autowired
    private ReservationService reservationService;
    
    @GetMapping("/user")
    public String mainControll(Model model){
        return "index";
    }

    
    @GetMapping("/booking")
    public String showReservations(HttpSession session , Model model) {

        // 회의실 목록 가져오기
        List<CabinetDTO> allCabinets = cabinetService.getAllCabinets();

        model.addAttribute("cabinets",allCabinets);

        return "/booking/booking";
    }

    @GetMapping("/user/inquiry")
    public String inquiryPage() {
        return "/inquiry/inquiry";
    }

    @GetMapping("/user/approvalMain")
    public String approvalMainController(){
        return "/approvals/approvalMain";
    }

    @GetMapping("/user/notification")
    public String notification() {
        return "/board/notification";
    }

    @GetMapping("/user/allWork")
    public String sharedWork(){
        return "/sharedWork/allWork";
    }
  
    @GetMapping("/auth/login")
    public void loginPage(){};



    @GetMapping("/admin/main")
    public String adminPage(){
        return "admin/admin_main";
    };

    @GetMapping("user/myPage")
    public String myPage(HttpSession session, Model model){

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        String deptName = memberService.findDeptName(user.getDeptCode());
        String positionName = memberService.findPositionName(user.getPositionValue());

        model.addAttribute("deptName",deptName);
        model.addAttribute("positionName",positionName);

        return "myPage/myPage";
    }

    @GetMapping("/user/mail")
    public String mailPage(Model model , HttpSession session){
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        String senderEmpId = loginUser.getEmpId(); // 보낸 사람의 empId로 설정

        List<MailDTO> sentMails = mailService.getSentMails(senderEmpId); // 보낸 메일만 가져오기
        model.addAttribute("sentMails", sentMails); // 보낸 메일만 모델에 추가

        return "/mail/mailMain"; // 전체 메일 페이지로 리턴
    }

    @GetMapping("/user/product")
    public String zasaPage(){
        return "zasaPage/product";
    }

}

