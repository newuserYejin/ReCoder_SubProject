package com.ohgiraffers.refactorial.user.controller;

import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import com.ohgiraffers.refactorial.user.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/*")
public class UserController {

    private MemberService memberService;

    @Autowired
    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("addEmployee")
    public ModelAndView addEmployee(ModelAndView mv, @ModelAttribute UserDTO userDTO){

        System.out.println("userDTO = " + userDTO);

        Integer result = memberService.addEmployee(userDTO);

        String message = null;

        if (result == null){        // id값 중복
            message = "중복된 회원이 존재합니다.";
        } else if (result == 0){        // insert 구문이 실행되다가 실패
            message = "서번 내부에서 오류가 발생했습니다.";
            mv.setViewName("index");
        } else if (result >= 1) {
            message = "회원 가입이 완료되었습니다.";
            mv.setViewName("index");
        }

        mv.addObject("message", message);

        return mv;
    }

}
