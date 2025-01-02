package com.ohgiraffers.refactorial.fileUploade.controller;

import com.ohgiraffers.refactorial.fileUploade.model.dto.UploadFileDTO;
import com.ohgiraffers.refactorial.fileUploade.model.service.UploadFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/files/*")
public class UploadFileController {

    private final UploadFileService uploadService;

    @Autowired
    public UploadFileController(UploadFileService uploadService){
        this.uploadService = uploadService;
    }

    @GetMapping("downLoad")
    public void downLoadFile(@RequestParam String fileId, HttpServletResponse response) throws IOException {
        System.out.println("fileId = " + fileId);

        UploadFileDTO uploadFile = uploadService.findFileByFileId(fileId);

        System.out.println("uploadFile = " + uploadFile);

        String storeFileName = uploadFile.getStoreFileName();
        String fileType = uploadFile.getFileType();
        String fileName = uploadFile.getOriginFileName();
        // 현재 프로젝트 경로
        String projectPath = System.getProperty("user.dir");

        String filePath;

        if (fileType.equals(".jpg") || fileType.equals(".png") || fileType.equals(".svg")){
            filePath = projectPath + "/src/main/resources/static/images/uploadImg/" + storeFileName;
        } else {
            filePath = projectPath + "/src/main/resources/static/files/" + storeFileName;
        }

        File file = new File(filePath);

        if (file.exists()){
            response.setContentType("application/octet-stream");
            response.setContentLength(Integer.parseInt(String.valueOf(uploadFile.getFileSize())));
            response.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode(fileName, StandardCharsets.UTF_8)+"\";");
            response.setHeader("Content-Transfer-Encoding","binary");

            // 파일을 읽고, 클라이언트에게 전송
            try (BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
                 OutputStream outStream = response.getOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                // 파일을 버퍼에 읽어서 출력 스트림에 씁니다.
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                outStream.flush();  // 출력 스트림을 플러시합니다.
            }
        } else {
            response.setContentType("text/plain");
            response.getWriter().write("파일에 문제가 있습니다.");  // 오류 메시지
        }
    }

}
