package com.ohgiraffers.refactorial.sharedWork.controller;

import com.ohgiraffers.refactorial.sharedWork.model.dto.SharedWorkDTO;
import com.ohgiraffers.refactorial.sharedWork.service.SharedWorkService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/allWork")
public class SharedWorkController {

    private final SharedWorkService sharedService;

    @Autowired
    public SharedWorkController(SharedWorkService sharedService) {
        this.sharedService = sharedService;
    }

    @GetMapping("allWork")
    public String allWork(@RequestParam String workID,                    // 업무고유 ID
                          @RequestParam String workTitle,                 // 업무 제목
                          @RequestParam String workExplanation,           // 업무 설명
                          @RequestParam LocalDateTime deadLine,           // 마감기한
                          @RequestParam LocalDateTime workSchedule,       // 일정날짜
                          @RequestParam int deptCode,                     // 부서코드
                          HttpSession session) {

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");     // 로그인한 유저의 정보를 가져옴

        SharedWorkDTO allSchedule = new SharedWorkDTO();    // allSchedule 객체에 밑에 값들을 담음
        allSchedule.setWorkID(workID);
        allSchedule.setWorkTitle(workTitle);
        allSchedule.setWorkExplanation(workExplanation);
        allSchedule.setDeadLine(deadLine);
        allSchedule.setWorkSchedule(workSchedule);
        allSchedule.setDeptCode(user.getDeptCode());



//        sharedService.allWork(allSchedule);



        return " ";
    }


    private String workID;              // 업무고유 ID
    private String workTitle;           // 업무 제목
    private String workExplanation;     // 업무 설명
    private LocalDateTime deadLine;     // 마감기한
    private LocalDateTime workSchedule; // 일정날짜
    private String deptCode;            // 부서코드



}
