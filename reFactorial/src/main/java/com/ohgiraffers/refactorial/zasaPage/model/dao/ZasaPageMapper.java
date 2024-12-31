package com.ohgiraffers.refactorial.zasaPage.model.dao;

import com.ohgiraffers.refactorial.zasaPage.model.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZasaPageMapper {

    List<ProductDTO> getAllProducts();

}
