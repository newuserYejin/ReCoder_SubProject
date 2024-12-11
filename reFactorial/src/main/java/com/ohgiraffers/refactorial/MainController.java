package com.ohgiraffers.refactorial;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.service.ReservationService;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
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

    @GetMapping("/user/booking")
    public String showReservations(HttpSession session , Model model) {
        UserDTO user = (UserDTO) session.getAttribute("LoginUserInfo");
        // 전체 예약 목록
        List<ReservationDTO> allReservations = reservationService.getAllReservations();
        model.addAttribute("allReservations", allReservations); // 전체 예약 정보 추가

        // 사용자 예약 목록
        List<ReservationDTO> userReservations = reservationService.getUserReservations(user.getEmpId());
        model.addAttribute("userReservations", userReservations); // 사용자 예약 정보 추가

        return "/booking/booking"; // 예약 페이지로 반환
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

    @GetMapping("/user/sharedWork")
    public String sharedWork(){
        return "/board/sharedWork";
    }
  
    @GetMapping("/auth/login")
    public void loginPage(){};

    @GetMapping("/user/addressBookMain")
    public String addressBookPage(){
        return "/addressbook/addressBookMain";
    }

    @GetMapping("/admin/main")
    public String adminPage(){
        return "admin/admin_main";
    };

    @GetMapping("user/myPage")
    public String myPage(HttpSession session, Model model){

        UserDTO user = (UserDTO) session.getAttribute("LoginUserInfo");

        String deptName = memberService.findDeptName(user.getDeptCode());
        String positionName = memberService.findPositionName(user.getPositionValue());

        model.addAttribute("deptName",deptName);
        model.addAttribute("positionName",positionName);

        return "myPage/myPage";
    }

}

