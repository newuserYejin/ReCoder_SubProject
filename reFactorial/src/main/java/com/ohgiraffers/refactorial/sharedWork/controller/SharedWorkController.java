package com.ohgiraffers.refactorial.sharedWork.controller;

import com.ohgiraffers.refactorial.sharedWork.model.dto.SharedWorkDTO;
import com.ohgiraffers.refactorial.sharedWork.service.SharedWorkService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class SharedWorkController {

    private final SharedWorkService sharedService;

    @Autowired
    public SharedWorkController(SharedWorkService sharedService) {
        this.sharedService = sharedService;
    }

    // 전체 업무 조회
    @GetMapping("/event")
    @ResponseBody
    public List<SharedWorkDTO> getAllSharedWork(HttpSession session) {

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");   // 로그인한 유저 정보를 가져옴

        if (user == null) {
            throw new IllegalStateException("유효한 사용자 세션이 아닙니다.");
        }

        int deptCode = user.getDeptCode();

        return sharedService.getAllSharedWork(deptCode);
    }

    // 일정 저장
    @PostMapping("/allWork")
    @ResponseBody
    public String saveSharedWork(@RequestBody Map<String, Object> eventData, HttpSession session) {
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");   // 로그인한 유저 정보를 가져옴

        if (user == null) {
            return "유효한 사용자 세션이 아닙니다.";
        }

        // 고유 ID 생성
        String workID = "WO" + String.format("%05d", (int) (Math.random() * 100000));

        SharedWorkDTO sharedWork = new SharedWorkDTO();
        sharedWork.setWorkId(workID);
        sharedWork.setWorkTitle(String.valueOf(eventData.get("workTitle")));  // 업무제목
        sharedWork.setWorkExplanation(String.valueOf(eventData.get("workExplanation")));  // 업무설명

        // 날짜 포맷 검증 및 변환
        String workScheduleStr = (String) eventData.get("workSchedule");
        String deadLineStr = (String) eventData.get("deadLine");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            if (workScheduleStr != null && deadLineStr != null) {
                sharedWork.setWorkSchedule(LocalDate.parse(workScheduleStr, formatter));  // 시작 날짜
                sharedWork.setDeadLine(LocalDate.parse(deadLineStr, formatter));  // 마감 날짜
            } else {
                throw new IllegalArgumentException("날짜 값이 null입니다.");
            }
        } catch (DateTimeParseException e) {
            return "날짜 형식이 잘못되었습니다. 올바른 날짜 형식(yyyy-MM-dd)을 사용해주세요.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();  // "날짜 값이 null입니다."를 반환
        }

        sharedWork.setDeptCode(user.getDeptCode());  // 부서코드 설정

        sharedService.saveSharedWork(sharedWork);

        return "/sharedWork/allWork";

    }
}
