package com.ohgiraffers.refactorial.sharedWork.controller;

import com.ohgiraffers.refactorial.sharedWork.model.dto.SharedWorkDTO;
import com.ohgiraffers.refactorial.sharedWork.service.SharedWorkService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/allWork")
public class SharedWorkController {

    private final SharedWorkService sharedService;

    @Autowired
    public SharedWorkController(SharedWorkService sharedService) {
        this.sharedService = sharedService;
    }

    // 모든 업무 조회
    @GetMapping("/allWork")
    public List<SharedWorkDTO> getAllSharedWork() {
        return sharedService.getAllSharedWork();
    }

    // 업무 저장
    @PostMapping("/allWork")
    public String saveSharedWork(@RequestBody SharedWorkDTO sharedWork) {
        // 고유 ID 생성
        sharedWork.setWorkID(UUID.randomUUID().toString());
        sharedService.saveSharedWork(sharedWork);
        return "업무가 저장되었습니다.";
    }



//    @GetMapping("allWork")
//    public String allWork(@RequestParam String workID,                    // 업무고유 ID
//                          @RequestParam String workTitle,                 // 업무 제목
//                          @RequestParam String workExplanation,           // 업무 설명
//                          @RequestParam LocalDateTime deadLine,           // 마감기한
//                          @RequestParam LocalDateTime workSchedule,       // 일정날짜
//                          @RequestParam int deptCode,                     // 부서코드
//                          HttpSession session) {
//
//        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");     // 로그인한 유저의 정보를 가져옴
//
//        SharedWorkDTO allSchedule = new SharedWorkDTO();    // allSchedule 객체에 밑에 값들을 담음
//        allSchedule.setWorkID(workID);
//        allSchedule.setWorkTitle(workTitle);
//        allSchedule.setWorkExplanation(workExplanation);
//        allSchedule.setDeadLine(deadLine);
//        allSchedule.setWorkSchedule(workSchedule);
//        allSchedule.setDeptCode(user.getDeptCode());
//
//
//
////        sharedService.allWork(allSchedule);
//
//
//
//        return " ";
//    }
}
