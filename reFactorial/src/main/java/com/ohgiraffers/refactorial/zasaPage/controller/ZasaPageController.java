package com.ohgiraffers.refactorial.zasaPage.controller;

import com.ohgiraffers.refactorial.zasaPage.model.dto.ProductDTO;
import com.ohgiraffers.refactorial.zasaPage.service.ZasaPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/zasaPage")
public class ZasaPageController {

    private final ZasaPageService zasaPageService;

    @Autowired
    public ZasaPageController(ZasaPageService zasaPageService){
        this.zasaPageService = zasaPageService;
    }


    @GetMapping("product")
    public String zasaPage(Model model){

        List<ProductDTO> products = zasaPageService.getAllProducts();

        model.addAttribute("products",products);

        return "zasaPage/product";
    }


}
