package com.ohgiraffers.refactorial.common;

import lombok.Getter;

// enum : 열거형 상수들의 집합. 고정되어 있는 값들 처리하기 위해 사용
@Getter
public enum UserRole {

    // 상수 필드
    USER("USER"), ADMIN("ADMIN");

    private String role;

    UserRole(String role){this.role = role;}

}
