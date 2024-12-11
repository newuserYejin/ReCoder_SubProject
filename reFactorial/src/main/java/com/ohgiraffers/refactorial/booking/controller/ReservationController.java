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

    // 회의실 목록을 가져오는 메서드
//    @GetMapping("/booking")
//    public String showBookingPage(Model model) {
//
//    }

    // 회의실을 선택하여 예약 폼을 띄우는 메서드
    @GetMapping("/bookingForm")
    public String showBookingForm(@RequestParam("roomNo") BigDecimal roomNo, Model model) {
        model.addAttribute("roomNo", roomNo);
        return "booking/bookingForm";
    }

    // 예약을 처리하는 메서드
    @PostMapping("/reserve")
    public String makeReservation(@ModelAttribute ReservationDTO reservationDTO, Model model) {
        // 예약 ID 생성
        reservationDTO.setReservationId(UUID.randomUUID().toString());

        try {
            // 예약 처리
            reservationService.reserveConferenceRoom(reservationDTO);
            return "redirect:/user/booking";  // 예약 후, 예약 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "booking/bookingForm";  // 에러 발생 시 예약 폼으로 돌아가기
        }
    }

}
