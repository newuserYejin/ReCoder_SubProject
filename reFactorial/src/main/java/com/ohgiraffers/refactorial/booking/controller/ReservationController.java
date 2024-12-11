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
        // Ensure roomNo is passed correctly to the booking form page
        model.addAttribute("roomNo", roomNo);
        return "/bookingForm";
    }

    // 예약을 처리하는 메서드
    @PostMapping("/reserve")
    public String makeReservation(@ModelAttribute ReservationDTO reservationDTO, Model model) {
        reservationDTO.setReservationId(UUID.randomUUID().toString());

        // 예약 가능 여부 확인
        boolean isAvailable = reservationService.isReservationAvailable(reservationDTO.getReservationDate(),
                reservationDTO.getReservationStartTime(),
                reservationDTO.getReservationEndTime());

        if (!isAvailable) {
            model.addAttribute("errorMessage", "예약 시간이 중복됩니다. 다른 시간을 선택해 주세요.");
            return "booking/bookingForm"; // 중복 시 예약 폼으로 돌아가기
        }

        try {
            reservationService.reserveConferenceRoom(reservationDTO);
            return "redirect:/user/booking";  // 예약 후, 예약 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "booking/bookingForm";  // 에러 발생 시 예약 폼으로 돌아가기
        }
    }

    // 예약 삭제 메서드
    @PostMapping("/deleteReservation")
    public String deleteReservationById(@RequestParam("reservationId") String reservationId) {
        try {
            reservationService.deleteReservationById(reservationId);
            return "redirect:/user/booking"; // 삭제 후, 예약 목록 페이지로 리다이렉트
        } catch (RuntimeException e) {
            return "redirect:/user/booking";  // 삭제 중 오류 발생 시 에러 페이지로 이동
        }
    }
}
