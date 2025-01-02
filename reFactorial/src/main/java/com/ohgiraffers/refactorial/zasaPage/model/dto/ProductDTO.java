package com.ohgiraffers.refactorial.zasaPage.model.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductDTO {

    private String id;
    private String name;
    private String price;
    private String description;
    private String image;
    private String empId;
    private String category;
    private String status;
}
