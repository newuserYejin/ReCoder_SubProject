package com.ohgiraffers.refactorial.fileUploade.model.dao;

import com.ohgiraffers.refactorial.fileUploade.model.dto.UploadFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadFileMapper {

    // 파일 저장
    int addFile(UploadFileDTO uploadFile);

    List<UploadFileDTO> findFileByMappingId(String mappingId);

    UploadFileDTO findFileByFileId(String fileId);
}
