package com.ohgiraffers.refactorial.mail.model.dao;

import com.ohgiraffers.refactorial.mail.model.dto.MailEmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailEmployeeMapper {

    List<MailEmployeeDTO> searchMailEmployeesByName(String searchReceiverInput);

    List<MailEmployeeDTO> getAllMailEmployees();
}
