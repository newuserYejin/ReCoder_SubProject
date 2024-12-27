package com.ohgiraffers.refactorial.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**") // 브라우저에서 접근할 URL 경로
                .addResourceLocations("file:///C:/uploads/"); // 실제 저장 경로

        registry.addResourceHandler("/uploadImg/**")
                .addResourceLocations("file:///C:/Users/문정현/Desktop/그룹웨어프로젝트/reFactorial/src/main/resources/static/images/uploadImg/");
    }
}
