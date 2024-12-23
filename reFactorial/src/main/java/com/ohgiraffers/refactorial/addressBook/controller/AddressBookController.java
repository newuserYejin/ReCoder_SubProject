package com.ohgiraffers.refactorial.addressBook.controller;

import com.ohgiraffers.refactorial.addressBook.service.AddressBookService;
import com.ohgiraffers.refactorial.approval.model.dto.EmployeeDTO;
import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
