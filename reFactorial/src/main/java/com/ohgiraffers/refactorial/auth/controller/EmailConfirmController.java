package com.ohgiraffers.refactorial.auth.controller;

import com.ohgiraffers.refactorial.auth.service.EmailConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth/*")
public class EmailConfirmController {

    @Autowired
    private EmailConfirmService es;

    // 인증번호 저장
    private int number;

    // 재설정 비번 저장
    private String resetPw;

    @PostMapping(value = "matchEmpIdEmail" , produces = "application/json; charset=UTF-8;")
    @ResponseBody
    public Map<String, Object> matchEmpIdEmail(@RequestBody Map<String, String> request){

        Map<String, Object> matchEmpIdEmailStatus  = new HashMap<>();

        String confirmEmpId = request.get("confirmEmpId");
        String confirmEmpEmail = request.get("confirmEmpEmail");

        matchEmpIdEmailStatus = es.matchEmpIdEmail(confirmEmpId,confirmEmpEmail);

        return matchEmpIdEmailStatus;
    }

    @PostMapping(value ="sendVerificationCode" , produces = "application/json; charset=UTF-8;")
    @ResponseBody
    public Map<String, Object> sendVerificationCode(@RequestBody Map<String, String> request){

        String sendToEmail = request.get("sendToEmail");
        String emailTitle = "요청하신 인증번호 입니다.";
        Map<String, Object> result = new HashMap<>();

        String version = "code";

        try {
            number = (int) es.sendVerificationCode(sendToEmail,emailTitle,version);
            String num = String.valueOf(number);

            result.put("success", Boolean.TRUE);
            result.put("number", num);
        } catch (Exception e) {
            result.put("success", Boolean.FALSE);
            result.put("error", e.getMessage());
        }

        return result;
    }

    @PostMapping(value = "ResetPwd" , produces = "application/json; charset=UTF-8;")
    @ResponseBody
    public Map<String, Object> ResetPwd(ModelAndView mv, @RequestBody Map<String, String> request){
        String sendToEmail = request.get("sendToEmail");
        String targetEmpId = request.get("targetEmpId");

        String emailTitle = "재설정 된 비밀번호 입니다.";

        String version = "pwd";

        Map<String, Object> result = new HashMap<>();

        try {
            resetPw = String.valueOf( es.sendVerificationCode(sendToEmail,emailTitle,version) );

            int updatePwResult = es.setResetPw(resetPw,targetEmpId);

            result.put("resetSuccess", Boolean.TRUE);
            result.put("resetPw", resetPw);
            result.put("updatePwResult",updatePwResult);
        } catch (Exception e) {
            result.put("resetSuccess", Boolean.FALSE);
            result.put("error", e.getMessage());
        }

        return result;
    }

}
