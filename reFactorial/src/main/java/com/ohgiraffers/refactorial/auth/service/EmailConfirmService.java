package com.ohgiraffers.refactorial.auth.service;

import com.ohgiraffers.refactorial.user.model.dao.UserMapper;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailConfirmService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    // 인증번호 저장
    private static int number;
    private JavaMailSender javaMailSender;
    private static final String senderEmail= "jin200126@gmail.com";
    private static String resetPw;

    public EmailConfirmService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Map<String, Object> matchEmpIdEmail(String confirmEmpId, String confirmEmpEmail) {

        UserDTO user = userMapper.findByUsername(confirmEmpId);

        Map<String, Object> result = new HashMap<>();

        if (user != null){
            if (user.getEmpEmail().equals(confirmEmpEmail)) {
                result.put("matchEmpIdEmailStatus", true);
            }else{
                result.put("matchEmpIdEmailStatus", false);
            }
        } else {
            result.put("msgError","해당 사원번호의 사원이 없습니다.");
        }

        return result;
    }

    // 랜덤으로 숫자 생성
    public static void createNumber() {
        number = (int)(Math.random() * (90000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
    }
    
    // 랜덤 문자열 생성
    public static void createRandomString(){
        Random random = new Random();

        StringBuilder list = new StringBuilder();
        for(int i=0; i<6; i++) {
            int n = random.nextInt(36);

            if(n>25) {
                list.append(n-25);
            } else {
                list.append((char)(n+65));
            }
        }

        resetPw = list.toString();
    }

    public Object sendVerificationCode(String sendToEmail, String emailTitle, String version) {

        if ("code".equals(version)){
            createNumber();

            MimeMessage message = CreateMail(sendToEmail,emailTitle,number);
            javaMailSender.send(message);
            return number;

        } else if ("pwd".equals(version)) {
            createRandomString();

            MimeMessage message = CreateMail(sendToEmail,emailTitle,resetPw);
            javaMailSender.send(message);

            return resetPw;
        }

        return number;
    }

    private MimeMessage CreateMail(String sendToEmail, String emailTitle,Object sendValue) {

        MimeMessage message = javaMailSender.createMimeMessage();

        sendToEmail = "jin200126@gmail.com";


        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, sendToEmail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + emailTitle + "</h3>";
            body += "<h1>" + String.valueOf(sendValue) + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        }catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    public Integer setResetPw(String resetPw, String targetEmpId) {

        String enResetPw = encoder.encode(resetPw);

        // useMapper 공용 사용
        Map<String, String> updateData = new HashMap<>();
        updateData.put("enChangePW",enResetPw);
        updateData.put("empId",targetEmpId);

        int result =  userMapper.changePW(updateData);

        return result;
    }
}
