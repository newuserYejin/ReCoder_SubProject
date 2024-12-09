package com.ohgiraffers.refactorial.booking.controller;

import com.ohgiraffers.refactorial.auth.model.AuthDetails;
import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.service.ReservationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/bookingForm")
    public String bookingForm(@RequestParam("roomNo") String roomNo, Model model , @AuthenticationPrincipal AuthDetails authenticatedUser) {
        model.addAttribute("roomNo", roomNo);
        model.addAttribute("authenticatedUser", authenticatedUser);
        return "/booking/bookingForm"; // 타임리프 템플릿 이름
    }

    @PostMapping("/reserve")
    public String reserve(ReservationDTO reservationDTO) {
        reservationService.createReservation(reservationDTO);
        return "redirect:/"; // 예약 후 리다이렉트할 페이지
    }
}
