package com.ohgiraffers.refactorial;

import com.ohgiraffers.refactorial.attendance.service.AttendanceService;
import com.ohgiraffers.refactorial.board.model.dto.BoardDTO;
import com.ohgiraffers.refactorial.board.model.dto.CommentDTO;
import com.ohgiraffers.refactorial.board.service.BoardService;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final MemberService memberService;
    private final CabinetService cabinetService;
    private final MailService mailService;
    private final AttendanceService attendanceService;
    private final BoardService boardService;

    @Autowired
    public MainController(MemberService memberService,
                          CabinetService cabinetService,
                          MailService mailService,
                          AttendanceService attendanceService,
                          BoardService boardService
                        ){
        this.memberService = memberService;
        this.cabinetService = cabinetService;
        this.mailService = mailService;
        this.attendanceService = attendanceService;
        this.boardService = boardService;
    }


    @GetMapping("/")
    public String defaultURL(Model model){
        return "auth/login";
    }

    @Autowired
    private ReservationService reservationService;
    
    @GetMapping("/user")
    public String mainControll(Model model,HttpSession session){
        List<BoardDTO> boardList = boardService.postList(1);

        model.addAttribute("boardList",boardList);

        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        String empId = loginUser.getEmpId(); // 보낸 사람의 empId로 설정

        // 내가 받은 메일만 가져오기, 내가 보낸 메일은 제외
        List<MailDTO> receivedMails = mailService.getReceivedMails(empId);
        
        Map<String,Object> findSender = new HashMap<>();

        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
        
        for (MailDTO mail : receivedMails){
            String name = memberService.getNameById(mail.getSenderEmpId());

            String date = (smp.format(mail.getSentDate()));

            Map<String, Object> mailWithDate = new HashMap<>();
            mailWithDate.put("mail", mail);  // 기존 mail 객체 저장
            mailWithDate.put("date", date);  // 날짜 정보 추가
            
            findSender.put(name, mailWithDate);
        }

        System.out.println("findSender = " + findSender);
        
        // 자신에게 보낸 메일을 제외한 받은 메일만 모델에 추가
        model.addAttribute("receivedMails", findSender);

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
    public String adminPage(Model model){

        // 입사 현황
        Map<String, Object> empHiredDateChart = memberService.getHiredDateGroupBy();
        
        // 오늘 출근 현황
        LocalDate today = LocalDate.now();
        Map<String,Object> attendanceChart = attendanceService.getAttendanceGroupBy(today);

        // 최근 이벤트 게시물
        List<BoardDTO> eventList = boardService.postList(5);

        if (eventList != null && !eventList.isEmpty()){
            model.addAttribute("recentlyEvent",eventList.get(0));
            String postId = eventList.get(0).getPostId();
            List<CommentDTO> commentList = boardService.commentView(postId);
            model.addAttribute("commentList",commentList);
        }


        model.addAttribute("attendanceChart",attendanceChart);
        model.addAttribute("empHiredDateChart",empHiredDateChart);

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
        String receiverEmpId = loginUser.getEmpId(); // 받은 사람의 empId로 설정

        // 내가 보낸 메일만 가져오기
        List<MailDTO> sentMails = mailService.getSentMails(senderEmpId);
        model.addAttribute("sentMails", sentMails);

        // 내가 받은 메일만 가져오기, 내가 보낸 메일은 제외
        List<MailDTO> receivedMails = mailService.getReceivedMails(receiverEmpId);
        // 자신에게 보낸 메일을 제외한 받은 메일만 모델에 추가
        receivedMails.removeIf(mail -> mail.getSenderEmpId().equals(receiverEmpId));
        model.addAttribute("receivedMails", receivedMails);

        return "/mail/mailMain"; // 전체 메일 페이지로 리턴
    }



}

