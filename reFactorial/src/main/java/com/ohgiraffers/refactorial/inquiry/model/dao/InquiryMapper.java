package com.ohgiraffers.refactorial.inquiry.model.dao;

import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InquiryMapper {
    void sendInquiry(InquiryDTO inquiryDTO);
}
