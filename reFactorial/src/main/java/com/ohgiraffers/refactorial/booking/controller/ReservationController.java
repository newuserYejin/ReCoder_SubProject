package com.ohgiraffers.refactorial.booking.controller;

import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 회의실을 선택하여 예약 폼을 띄우는 메서드
    @GetMapping("/bookingForm")
    public String showBookingForm(@RequestParam("roomNo") BigDecimal roomNo, Model model) {
        model.addAttribute("roomNo", roomNo);
        return "booking/bookingForm";
    }

}
