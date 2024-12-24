package com.ohgiraffers.refactorial.user.model.dto;

import com.ohgiraffers.refactorial.common.UserRole;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LoginUserDTO {

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
    private String deptName;
    private int positionValue;
    private String positionName;
    private UserRole viewAuth;

    public List<String> getRole() {

        if (this.viewAuth.getRole().length() > 0){
            // 권한이 1개 이상 있을 경우
            return Arrays.asList(this.viewAuth.getRole().split(","));
        }
        return new ArrayList<>();

    }

}
