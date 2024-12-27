package com.ohgiraffers.refactorial.inquiry.model.dao;

import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    void sendInquiry(InquiryDTO inquiryDTO);

    List<InquiryDTO> sentInquiries(String senderEmpId);
}
