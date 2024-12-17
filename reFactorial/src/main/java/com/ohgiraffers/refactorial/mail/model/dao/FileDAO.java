package com.ohgiraffers.refactorial.mail.model.dao;

import com.ohgiraffers.refactorial.mail.model.dto.FileDTO;

import java.util.List;

public interface FileDAO {

    void insertAttachments(List<FileDTO> fileDTOList);
}
