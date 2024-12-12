package com.ohgiraffers.refactorial.approval.controller;

import com.ohgiraffers.refactorial.approval.model.dto.ApprovalRequestDTO;
import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import com.ohgiraffers.refactorial.approval.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/approvals") // 공통 경로 설정
public class ApprovalController {

    private final ApprovalService approvalService;

    @Autowired
    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }


    @GetMapping("approvalWaiting")
    public String waitingPage(){
    return "/approvals/approvalWaiting";
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
        model.addAttribute("referrers", referrers);
        return "/approvals/searchReferrers";
    }

    @PostMapping("/submitApproval")
    public String submitApproval(@ModelAttribute ApprovalRequestDTO approvalRequestDTO){

        // 1.이것은 결제문서 저장
        String pmId = approvalService.saveApproval(approvalRequestDTO);

        // 2.이것은 승인자 저장
        approvalService.saveApprovers(pmId,approvalRequestDTO.getApprovers());

        // 3.이것은 참조자 저장
        approvalService.saveReferrers(pmId,approvalRequestDTO.getReferrers());

        return "/approvals/approvalMain";
    }

    @GetMapping("Waiting")
    public String getApprovalWaiting(Model model) {
        List<DocumentDTO> waitingDocs = approvalService.getWaitingDocuments();
        model.addAttribute("documents", waitingDocs);
        return "/approvals/approvalWaiting";
    }

    @GetMapping("referenceDocuments")
    public String getReferenceDocuments(Model model) {
        List<DocumentDTO> referenceDocs = approvalService.getReferenceDocuments();
        model.addAttribute("documents", referenceDocs);
        return "/approvals/referenceDocuments";
    }
    

}
