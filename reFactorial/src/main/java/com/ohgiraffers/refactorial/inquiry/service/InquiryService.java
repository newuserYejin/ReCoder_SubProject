package com.ohgiraffers.refactorial.inquiry.service;

import com.ohgiraffers.refactorial.fileUploade.model.service.UploadFileService;
import com.ohgiraffers.refactorial.inquiry.model.dao.InquiryMapper;
import com.ohgiraffers.refactorial.inquiry.model.dto.InquiryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InquiryService {

    private InquiryMapper inquiryMapper;
    private UploadFileService uploadFileService;

    @Autowired
    public InquiryService(InquiryMapper inquiryMapper, UploadFileService uploadFileService) {
        this.inquiryMapper = inquiryMapper;
        this.uploadFileService = uploadFileService;
    }

    public void sendInquiry(InquiryDTO inquiryDTO, List<MultipartFile> inquiryFileList) throws IOException {
        String iqrId = inquiryDTO.getIqrValue();

        // 파일 업로드
        if (!inquiryFileList.isEmpty()) {
            inquiryDTO.setInquiryFile(inquiryFileList);
            inquiryDTO.setAttachment(1);
            uploadFileService.upLoadFile(inquiryFileList, iqrId);  // emId를 mappingId로 사용
        }
        inquiryMapper.sendInquiry(inquiryDTO);
    }
    public List<InquiryDTO> sentInquiries(String senderEmpId) {
        return inquiryMapper.sentInquiries(senderEmpId);
    }

    public InquiryDTO getInquiryDetail(String iqrValue) {
        return inquiryMapper.selectInquiryDetail(iqrValue);
    }
}
