package com.ohgiraffers.refactorial.approval.model.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EmployeeDTO {

    private String name;          // emp_name
    private String no;  // emp_no
    private String address;       // emp_address
    private String email;         // emp_email
    private String employeeId;    // emp_id
    private Date joined;      // emp_joined
    private String annualLeave;   // annual_leave
    private String password;      // emp_pwd
    private String profileImage;  // profile
    private String phone;         // emp_phone
    private int departmentCode;   // dept_code
    private int positionValue;    // position_value
    private String viewAuth;      // view_auth


}
