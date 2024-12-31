package com.ohgiraffers.refactorial.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(100)); // 최대 파일 크기
        factory.setMaxRequestSize(DataSize.ofMegabytes(100)); // 전체 요청 크기
        return factory.createMultipartConfig();
    }
}
