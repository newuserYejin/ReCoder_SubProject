package com.ohgiraffers.refactorial.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**") // 브라우저에서 접근할 URL 경로
                .addResourceLocations("file:///C:/uploads/"); // 실제 저장 경로

        // 현재 컴퓨터에서의 프로젝트까지 절대 경로 자동 가져오기
        String projectRootPath = System.getProperty("user.dir");
        String uploadPath = projectRootPath + "/src/main/resources/static/images/uploadImg/";

        registry.addResourceHandler("/uploadImg/**")
                .addResourceLocations("file:///"+uploadPath.replace("\\", "/"));
    }
}
