package com.ohgiraffers.refactorial.approval.controller;

import com.ohgiraffers.refactorial.approval.model.dto.ApprovalRequestDTO;
import com.ohgiraffers.refactorial.approval.model.dto.DocumentDTO;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import com.ohgiraffers.refactorial.approval.model.dto.FileDTO;
import com.ohgiraffers.refactorial.approval.service.ApprovalService;
import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public String submitApproval(@ModelAttribute ApprovalRequestDTO approvalRequestDTO,
                                 @RequestParam("file") MultipartFile file,
                                 Model model,
                                 HttpSession session) {

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        if (user == null) {
            model.addAttribute("errorMessage", "로그인 정보가 없습니다. 다시 로그인해주세요.");
            return "redirect:/login";
        }

        String creatorId = user.getEmpId();

        // 승인자 이름 가져오기
        String firstApproverName = approvalRequestDTO.getFirstApprover();
        String midApproverName = approvalRequestDTO.getMidApprover();
        String finalApproverName = approvalRequestDTO.getFinalApprover();

        // 참조자 가져오기
        List<String> referrers = approvalRequestDTO.getReferrers();

        if (firstApproverName == null || midApproverName == null || finalApproverName == null) {
            model.addAttribute("errorMessage", "승인자 3명을 모두 입력해야 합니다.");
            return "/approvals/approvalPage";
        }

        // 이름으로 emp_id 조회
        String firstApproverId = approvalService.findEmpIdByName(firstApproverName);
        String midApproverId = approvalService.findEmpIdByName(midApproverName);
        String finalApproverId = approvalService.findEmpIdByName(finalApproverName);

        // 결재문서 저장
        String pmId = approvalService.saveApproval(approvalRequestDTO, creatorId);

        // 승인자 저장 (순서 포함)
        List<Map<String, Object>> approvers = new ArrayList<>();
        approvers.add(Map.of("empId", firstApproverId, "order", 1));
        approvers.add(Map.of("empId", midApproverId, "order", 2));
        approvers.add(Map.of("empId", finalApproverId, "order", 3));
        approvalService.saveApprovers(pmId, approvers);

        // 참조자 저장
        List<String> referrerIds = approvalService.findEmpIdsByNames(referrers);
        approvalService.saveReferrers(pmId, referrerIds);

        // 첨부파일 저장
        if (!file.isEmpty()) {
            try {
                approvalService.saveApprovalFile(pmId, file.getOriginalFilename(), "C:/uploads/" + file.getOriginalFilename(), file.getSize(), file.getContentType());
                file.transferTo(new File("C:/uploads/" + file.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "파일 업로드 실패");
                return "/approvals/approvalPage";
            }
        }

        return "/approvals/approvalMain";
    }







    // 대기 중
    @GetMapping("waiting")
    public String getApprovalWaiting(@RequestParam(value = "page", defaultValue = "1") int currentPage,Model model, HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        // 세션에서 로그인 사용자 정보 가져오기
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        if (user == null) {
            model.addAttribute("errorMessage", "로그인 정보가 없습니다. 다시 로그인해주세요.");
            return "redirect:/login";
        }

        // emp_id를 로그로 확인
        String loggedInEmpId = user.getEmpId();
        System.out.println("현재 로그인한 사용자 ID: " + loggedInEmpId);

        int limit = 14;  // 한 페이지에 14개 문서
        int offset = (currentPage - 1) * limit;  // offset 계산

        // 대기 중 문서 조회
        List<DocumentDTO> waitingDocs = approvalService.getWaitingDocuments(loggedInEmpId, limit, offset);


        int totalDocuments = approvalService.getWaitingCount(loggedInEmpId);
        int totalPages = (int) Math.ceil((double) totalDocuments / limit);  // 총 페이지 수 계산

        // 문서 번호 설정 (현재 페이지에 맞는 번호)
        for (int i = 0; i < waitingDocs.size(); i++) {
            waitingDocs.get(i).setRowNum(totalDocuments - offset - i);  // 번호는 현재 페이지를 기준으로 설정
        }

        // 이전 페이지 번호 계산
        int prevPage = (currentPage - 1 < 1) ? 1 : currentPage - 1;
        // 다음 페이지 번호 계산
        int nextPage = (currentPage + 1 > totalPages) ? totalPages : currentPage + 1;

        model.addAttribute("documents", waitingDocs);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);


        return "/approvals/waiting";
    }



    @GetMapping("referenceDocuments")
    public String getReferenceDocuments(@RequestParam(defaultValue = "1") int currentPage, Model model, HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        // 세션에서 로그인 사용자 정보 가져오기
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        if (user == null) {
            model.addAttribute("errorMessage", "로그인 정보가 없습니다. 다시 로그인해주세요.");
            return "redirect:/login";
        }

        String loggedInEmpId = user.getEmpId();

        int limit = 14;  // 한 페이지에 14개 문서
        int offset = (currentPage - 1) * limit;  // offset 계산

        // 참조자 문서 조회 (limit, offset 추가)
        List<DocumentDTO> referenceDocs = approvalService.getReferenceDocuments(loggedInEmpId, limit, offset);

        // 참조 문서 목록 출력 (디버깅)
        System.out.println("참조 문서 목록: " + referenceDocs);

        // 최신 글이 위로 정렬되도록 번호를 매기기
        int totalCount = referenceDocs.size();
        for (int i = 0; i < referenceDocs.size(); i++) {
            referenceDocs.get(i).setRowNum(totalCount - i); // 최신 글일수록 높은 번호
        }

        // 총 문서 개수로 페이지 수 계산
        int totalDocuments = approvalService.getTotalReferenceDocuments(loggedInEmpId);
        int totalPages = (int) Math.ceil((double) totalDocuments / limit);  // 총 페이지 수 계산

        // 문서 번호 설정 (현재 페이지에 맞는 번호)
        for (int i = 0; i < referenceDocs.size(); i++) {
            referenceDocs.get(i).setRowNum(totalDocuments - offset - i);  // 번호는 현재 페이지를 기준으로 설정
        }

        // 이전 페이지 번호 계산
        int prevPage = (currentPage - 1 < 1) ? 1 : currentPage - 1;
        // 다음 페이지 번호 계산
        int nextPage = (currentPage + 1 > totalPages) ? totalPages : currentPage + 1;

        model.addAttribute("documents", referenceDocs);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);
        return "approvals/referenceDocuments";
    }



    @GetMapping("myDocuments")
    public String getMyDocuments(@RequestParam(value = "page", defaultValue = "1") int currentPage, Model model, HttpSession session) {
        // 세션에서 로그인한 사용자 정보 가져오기
        // 세션에서 로그인 사용자 정보 가져오기
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");

        if (user == null) {
            model.addAttribute("errorMessage", "로그인 정보가 없습니다. 다시 로그인해주세요.");
            return "redirect:/login";
        }

        String loggedInEmpId = user.getEmpId();
        int limit = 14;  // 한 페이지에 14개 문서
        int offset = (currentPage - 1) * limit;  // offset 계산

        // 작성자가 작성한 문서 가져오기
        List<DocumentDTO> myDocuments = approvalService.getMyDocuments(loggedInEmpId, limit, offset);

        // 전체 문서 개수 가져오기
        int totalDocuments = approvalService.getMyDocumentsCount(loggedInEmpId);
        int totalPages = (int) Math.ceil((double) totalDocuments / limit);  // 총 페이지 수 계산

        // 문서 번호 설정 (현재 페이지에 맞는 번호)
        for (int i = 0; i < myDocuments.size(); i++) {
            myDocuments.get(i).setRowNum(totalDocuments - offset - i);  // 번호는 현재 페이지를 기준으로 설정
        }

        // 이전 페이지 번호 계산
        int prevPage = (currentPage - 1 < 1) ? 1 : currentPage - 1;
        // 다음 페이지 번호 계산
        int nextPage = (currentPage + 1 > totalPages) ? totalPages : currentPage + 1;

        model.addAttribute("documents", myDocuments);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);

        return "/approvals/myDocuments";
    }

    // 결제문서 상세페이지 조회
    @GetMapping("Detail/{pmId}")
    public String getApprovalDetail(@PathVariable("pmId") String pmId, Model model) {

        // pmId에 해당하는 결재 문서 정보 조회
        DocumentDTO document = approvalService.getDocumentById(pmId);

        if (document == null) {
            model.addAttribute("errorMessage", "해당 결제 문서를 찾을 수 없습니다.");
            return "errorPage"; // 에러 페이지로 리디렉션
        }

        // 작성자 정보 처리
        String creatorName = document.getCreatorName() != null ? document.getCreatorName() : "작성자 정보 없음";

        // 승인자 정보 처리 (최초, 중간, 최종)
        List<String> approverIds = document.getApprovers() != null
                ? Arrays.asList(document.getApprovers().split(","))
                : new ArrayList<>();
        String firstApprover = approverIds.size() > 0 ? approvalService.findEmpNameById(approverIds.get(0)) : "승인자 정보 없음";
        String midApprover = approverIds.size() > 1 ? approvalService.findEmpNameById(approverIds.get(1)) : "승인자 정보 없음";
        String finalApprover = approverIds.size() > 2 ? approvalService.findEmpNameById(approverIds.get(2)) : "승인자 정보 없음";

        // 참조자 정보 처리
        List<String> referrerIds = document.getReferrers() != null
                ? Arrays.asList(document.getReferrers().split(","))
                : new ArrayList<>();
        List<String> referrerNames = referrerIds.stream()
                .map(approvalService::findEmpNameById)
                .distinct()
                .collect(Collectors.toList());


        // 첨부파일 정보 처리 (null 및 빈 문자열 안전 처리)
        List<String> fileUrls = (document.getFileUrl() != null && !document.getFileUrl().isEmpty())
                ? Arrays.asList(document.getFileUrl().split(","))
                .stream()
                .map(fileName -> "/files/" + fileName) // URL 경로로 변환
                .collect(Collectors.toList())
                : new ArrayList<>();

        // 모델에 데이터 추가
        model.addAttribute("document", document);
        model.addAttribute("creatorName", creatorName);
        model.addAttribute("firstApprover", firstApprover);
        model.addAttribute("midApprover", midApprover);
        model.addAttribute("finalApprover", finalApprover);
        model.addAttribute("referrerNames", referrerNames);
        model.addAttribute("fileUrls", fileUrls);

        return "approvals/Detail";
    }

    @RestController
    @RequestMapping("/files")
    public class FileController {

        @Autowired
        private ApprovalService approvalService;

        // 파일 다운로드 (파일 ID로 조회)
        // 파일 다운로드 (파일 ID 기준)
        @GetMapping("/downloadById")
        public ResponseEntity<Resource> downloadFileById(@RequestParam int fileId) {
            try {
                FileDTO file = approvalService.getFileById(fileId);

                if (file == null || file.getFilePath() == null) {
                    return ResponseEntity.notFound().build();
                }

                Path filePath = Paths.get(file.getFilePath()).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                if (!resource.exists()) {
                    return ResponseEntity.notFound().build();
                }

                String encodedFileName = URLEncoder.encode(file.getFileName(), "UTF-8").replace("+", "%20");

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(file.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName)
                        .body(resource);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }
    }
        // 파일 다운로드 (파일 이름으로 조회)
        @GetMapping("/downloadByName")
        public ResponseEntity<Resource> downloadFileByName(@RequestParam String fileName) {
            try {
                FileDTO file = approvalService.getFileByFileName(fileName);

                if (file == null || file.getFilePath() == null) {
                    return ResponseEntity.notFound().build();
                }

                Path filePath = Paths.get(file.getFilePath()).normalize();
                Resource resource = new UrlResource(filePath.toUri());

                if (!resource.exists()) {
                    return ResponseEntity.notFound().build();
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(file.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                        .body(resource);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }
    }















