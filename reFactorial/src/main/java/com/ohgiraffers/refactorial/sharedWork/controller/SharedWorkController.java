package com.ohgiraffers.refactorial.sharedWork.controller;

import com.ohgiraffers.refactorial.sharedWork.service.SharedWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/SharedWork")
public class SharedWorkController {

    private final SharedWorkService sharedService;

    @Autowired
    public SharedWorkController(SharedWorkService sharedService) {
        this.sharedService = sharedService;
    }


}
