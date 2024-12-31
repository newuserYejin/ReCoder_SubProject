package com.ohgiraffers.refactorial.inquiry.model.dao;

import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminInquiryMapper {

    List<InquiryDTO> getAllInquiries();

    List<InquiryDTO> getAllNoAnswerInquires();

    List<InquiryDTO> getAllAnswerList();

    InquiryDTO adminInquiryDetail(String iqrValue);

    int updateAnswer(String iqrValue, String answerDetail);
}
