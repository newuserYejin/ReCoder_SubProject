package com.ohgiraffers.refactorial.user.model.dao;

import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;

public interface UserMapper {

    LoginUserDTO findByUsername(String username);
}
