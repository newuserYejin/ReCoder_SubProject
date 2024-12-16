package com.ohgiraffers.refactorial.admin.controller;

import com.ohgiraffers.refactorial.admin.model.service.AdminService;
import com.ohgiraffers.refactorial.attendance.dto.AttendanceDTO;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import com.ohgiraffers.refactorial.user.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService ms;

    @GetMapping("employee")
    public String adminAddEmployee(){ return "admin/admin_employee"; }

    @GetMapping("getAllEmployee")
    @ResponseBody
    public Map<String, Object> getAllEmployee(){
        Map<String, Object> result = new HashMap<>();

        List<LoginUserDTO> userList = adminService.getAllEmployee();

        result.put("userList",userList);

        return result;
    }

    @GetMapping("addEmployeeFragment")
    public String addEmployeeFragment(){
        return "admin/addEmployee";
    }

    @GetMapping("modifyEmpInfo")
    @ResponseBody
    public Map<String,Object> modifyEmpInfo( @RequestParam String empId){
        LoginUserDTO user = ms.findUserId(empId);

        Map<String, Object> result = new HashMap<>();

        result.put("ModifyUser", user);

        return result;
    }

    @PostMapping("modifyEmpInfo")
    public ModelAndView modifyEmpInfoUpdate(ModelAndView mv, @ModelAttribute UserDTO userDTO){

        Integer result = adminService.modifyEmpInfoUpdate(userDTO);
        
        if (result > 0){
            System.out.println("업데이트 완료");
        }

        mv.setViewName("admin/admin_employee");

        return mv;
    }

    @PostMapping("addEmployeeFragment")
    public String updateEmployeeFragment(@RequestBody Map<String, Object> res, Model model){

        System.out.println("res = " + res);

         Map<String, LoginUserDTO> user = (Map<String, LoginUserDTO>) res.get("ModifyUser");

        System.out.println("user = " + user);

        model.addAttribute("ModifyUser", user);

        return "/admin/addEmployee";
    }

    @GetMapping("getByDateAtt")
    @ResponseBody
    public Map<String, Object> getByDateAtt(@RequestParam String selectedDay,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size){
        // 전체 데이터의 개수
        int totalRecords = adminService.getTotalCountByDateAtt(selectedDay);

        System.out.println("totalRecords = " + totalRecords);

        // 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalRecords / size);

        // 건너뛸 갯수
        int offset = (page - 1) * size;

        List<AttendanceDTO> getByDateAttList = adminService.getByDateAtt(selectedDay,offset,size);

        Map<String, Object> result = new HashMap<>();
        result.put("items",getByDateAttList);
        result.put("totalPages",totalPages);

        return result;
    }

}
