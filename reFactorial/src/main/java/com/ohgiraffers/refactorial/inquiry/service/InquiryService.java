package com.ohgiraffers.refactorial.inquiry.service;

import com.ohgiraffers.refactorial.inquiry.model.dao.InquiryMapper;
import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InquiryService {

    private InquiryMapper inquiryMapper;

    @Autowired
    public InquiryService(InquiryMapper inquiryMapper) {
        this.inquiryMapper = inquiryMapper;
    }

    public void sendInquiry(InquiryDTO inquiryDTO) {

        // ID 생성 및 중복 방지 로직
        Set<String> generatedIds = new HashSet<>();
        String IQRid;
        do{
            IQRid = "IQR" + String.format("%05d", (int) (Math.random() * 100000));
        } while (!generatedIds.add(IQRid));

        inquiryDTO.setIqrValue(IQRid);

        inquiryMapper.sendInquiry(inquiryDTO);
    }

    public List<InquiryDTO> sentInquiries(String senderEmpId) {
        return inquiryMapper.sentInquiries(senderEmpId);
    }
}
