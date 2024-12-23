package com.ohgiraffers.refactorial.addressBook.controller;

import com.ohgiraffers.refactorial.addressBook.model.dto.FactoryDTO;
import com.ohgiraffers.refactorial.addressBook.service.AddressBookService;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/addressBook")
public class AddressBookController {

    private final AddressBookService addressBookService;
    private final UserMapper userMapper;


    @Autowired
    public AddressBookController(AddressBookService addressBookService,UserMapper userMapper){
        this.addressBookService = addressBookService;
        this.userMapper = userMapper;
    }

    @ResponseBody
    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return addressBookService.getAllEmployees();
    }

    @ResponseBody
    @GetMapping("/search")
    public List<EmployeeDTO> searchEmployees(@RequestParam("keyword") String keyword) {
        return addressBookService.searchEmployees(keyword);
    }

    @GetMapping("/main")
    public String getAddressBookPage(
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            Model model) {

        int limit = 10; // 한 페이지당 표시할 직원 수
        int totalEmployees = addressBookService.getEmployeeCount(); // 전체 직원 수
        int totalPages = totalEmployees > 0 ? (int) Math.ceil((double) totalEmployees / limit) : 1;

        // 현재 페이지 범위 검증
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        int offset = (currentPage - 1) * limit;

        // 현재 페이지의 직원 데이터 가져오기
        List<EmployeeDTO> employees = addressBookService.getEmployeesByPage(limit, offset);
        if (employees == null) { // null 방지
            employees = new ArrayList<>();
        }

        // 이전/다음 페이지 설정
        int prevPage = currentPage > 1 ? currentPage - 1 : 1;
        int nextPage = currentPage < totalPages ? currentPage + 1 : totalPages;

        // 모델에 데이터 추가
        model.addAttribute("employees", employees);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("prevPage", currentPage > 1 ? currentPage - 1 : 1);
        model.addAttribute("nextPage", currentPage < totalPages ? currentPage + 1 : totalPages);

        return "/addressBook/addressBookMain";
    }

//    @ResponseBody
//    @GetMapping("/partners")
//    public List<FactoryDTO> getAllFactories(){
//        return addressBookService.getAllFactories();
//    }
//
//    @ResponseBody
//    @GetMapping("searchPartners")
//    public List<FactoryDTO> searchFactories(@RequestParam("keyword") String keyword){
//        return addressBookService.searchFactories();
//    }

}
