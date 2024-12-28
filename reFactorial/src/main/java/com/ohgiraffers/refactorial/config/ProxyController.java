package com.ohgiraffers.refactorial.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProxyController {

    @Value("${naver.map.api-id}")
    private String naverApiId;

    @Value("${naver.map.api-key}")
    private String naverApiKey;

    @GetMapping("/proxy/geocode")
    public ResponseEntity<String> geocode(@RequestParam String query) {
        // 외부 API 요청 URL
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + query;

        // RestTemplate을 사용해서 외부 API 사용
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", naverApiId); // 네이버 API 키
        headers.set("X-NCP-APIGW-API-KEY", naverApiKey);

        // headers 포함시키기
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 외부 API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 응답 반환
        return ResponseEntity.ok(response.getBody());
    }
}
