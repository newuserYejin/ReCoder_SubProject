package com.ohgiraffers.refactorial.approval.controller;

import com.ohgiraffers.refactorial.approval.model.dto.ApprovalRequestDTO;
import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import com.ohgiraffers.refactorial.approval.service.ApprovalService;
import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import com.ohgiraffers.refactorial.user.model.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/approvals") // 공통 경로 설정
public class ApprovalController {

    private final ApprovalService approvalService;
    private final UserMapper userMapper; // UserMapper 주입

    @Autowired
    public ApprovalController(ApprovalService approvalService,UserMapper userMapper) {

        this.approvalService = approvalService;
        this.userMapper = userMapper;
    }


    @GetMapping("approvalPage")
    public String paymentPage(){

        return "/approvals/approvalPage";
}

    @GetMapping("searchEmployee")
    public String searchEmployeeController(@RequestParam("name") String name, Model model){
        List<EmployeeDTO> employees;
        if (name != null && !name.isEmpty()) {
            // 입력된 이름을 기반으로 검색
            employees = approvalService.searchByName(name);
        } else {
            // 이름이 없으면 전체 리스트 반환
            employees = approvalService.findAllEmployees();
        }

        // 부서명과 직책명을 추가로 조회하여 설정
        for (EmployeeDTO employee : employees) {
            String deptName = userMapper.findDeptName(employee.getDeptCode());
            String positionName = userMapper.findPositionName(employee.getPositionValue());
            employee.setDeptName(deptName);
            employee.setPositionName(positionName);
        }


        model.addAttribute("employees",employees);

        return "/approvals/searchEmployee";
    }

    @GetMapping("searchReferrers")
    public String searchReferrersController(@RequestParam(value = "name", required = false) String name, Model model) {
        List<EmployeeDTO> referrers;
        if (name != null && !name.isEmpty()) {
            referrers = approvalService.searchByReferrersPageName(name);
        } else {
            referrers = approvalService.findAllReferrers();
        }

        // 부서와 직책 이름 설정
        for (EmployeeDTO referrer : referrers) {
            String deptName = userMapper.findDeptName(referrer.getDeptCode());
            String positionName = userMapper.findPositionName(referrer.getPositionValue());
            referrer.setDeptName(deptName);
            referrer.setPositionName(positionName);
        }


        model.addAttribute("referrers", referrers);
        return "/approvals/searchReferrers";
    }

    @PostMapping("/submitApproval")
    public String submitApproval(@ModelAttribute ApprovalRequestDTO approvalRequestDTO, Model model, HttpSession session){

        // 세션에서 로그인 정보 가져오기
        UserDTO user = (UserDTO) session.getAttribute("LoginUserInfo");

        if (user == null) {
            model.addAttribute("errorMessage", "로그인 정보가 없습니다. 다시 로그인해주세요.");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        String creatorId = user.getEmpId(); // 세션 사용자 ID


        // 승인자 리스트 생성
        List<String> approvers = List.of(
                approvalRequestDTO.getFirstApprover(),
                approvalRequestDTO.getMidApprover(),
                approvalRequestDTO.getFinalApprover()
        ).stream().filter(approver -> approver != null && !approver.isEmpty()).toList();

        if (approvers.isEmpty()) {
            model.addAttribute("errorMessage", "승인자를 최소 한 명 이상 선택해야 합니다.");
            return "/approvals/approvalPage";
        }

        // 1.이것은 결제문서 저장
        String pmId = approvalService.saveApproval(approvalRequestDTO,creatorId);

        // 2.이것은 승인자 저장
        approvalService.saveApprovers(pmId,approvers);

        // 3.이것은 참조자 저장
        approvalService.saveReferrers(pmId,approvalRequestDTO.getReferrers());

        return "/approvals/approvalMain";
    }

    // 대기 중
    @GetMapping("waiting")
    public String getApprovalWaiting(Model model) {
        // 대기 중 문서 조회
        List<DocumentDTO> waitingDocs = approvalService.getWaitingDocuments();
        model.addAttribute("documents", waitingDocs);

        return "/approvals/waiting";
    }


//    @GetMapping("referenceDocuments")
//    public String getReferenceDocuments(Model model) {
//        List<DocumentDTO> referenceDocs = approvalService.getReferenceDocuments();
//        model.addAttribute("documents", referenceDocs);
//        return "/approvals/referenceDocuments";
//    }



    private String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }


}
