package com.ohgiraffers.refactorial.zasaPage.service;

import com.ohgiraffers.refactorial.zasaPage.model.dao.ZasaPageMapper;
import com.ohgiraffers.refactorial.zasaPage.model.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZasaPageService {

    @Autowired
    public ZasaPageMapper zasaPageMapper;

    public List<ProductDTO> getAllProducts() {
        return zasaPageMapper.getAllProducts();
    }


}
