package com.ohgiraffers.refactorial.board.model.dto;

import com.ohgiraffers.refactorial.common.UserRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {

    private String empId;
    private String empName;
    private String empNo;
    private String empAddress;
    private String empEmail;
    private String empJoined;
    private String annualLeave;
    private String usedAnnualLeave;
    private String empPwd;
    private String profile;
    private String empPhone;
    private int deptCode;
    private int positionValue;
    private UserRole viewAuth;

}
