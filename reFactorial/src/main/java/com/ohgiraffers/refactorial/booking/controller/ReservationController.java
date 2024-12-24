package com.ohgiraffers.refactorial.booking.controller;

import com.ohgiraffers.refactorial.booking.model.dto.CombineDTO;
import com.ohgiraffers.refactorial.booking.model.dto.ReservationDTO;
import com.ohgiraffers.refactorial.booking.service.ReservationService;
import com.ohgiraffers.refactorial.user.model.dto.LoginUserDTO;
import com.ohgiraffers.refactorial.user.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/booking/*")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 예약 메서드
    @PostMapping("reserve")
    @ResponseBody
    public Map<String, Object> makeReservation(@RequestBody ReservationDTO reservation,HttpSession session) {
        Map<String,Object> resultMap = new HashMap<>();

        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        reservation.setEmpId(user.getEmpId());

        //5자리 랜덤 문자열 생성
        String reservationId = "RE" + String.format("%05d", (int) (Math.random() * 100000));
        reservation.setReservationId(reservationId);

        int result = reservationService.addReserveRoom(reservation);

        if (result > 0){
            resultMap.put("msg",reservation.getReservationDate() + "에 예약되었습니다.");
        } else {
            resultMap.put("msg","예약에 실패했습니다.");
        }

        return resultMap;
    }

    // 회의실 별 날짜별 예약 내역 가져오기
    @GetMapping("/getReserveByRoomNo")
    @ResponseBody
    public Map<String, Object> getReserveByRoomNo(@RequestParam String selectedDate, @RequestParam String roomNo){
        Map<String, Object> result = new HashMap<>();

        List<ReservationDTO> reservationList = reservationService.getReserveByRoomNo(selectedDate,roomNo);

        if (reservationList == null){
            result.put("msg","아직 예약 없음");
        } else {
            result.put("reservationList",reservationList);
        }

        return result;
    }

    @GetMapping("/booking/myBookingList")
    public String myBookingList(HttpSession session, Model model) {
        LoginUserDTO user = (LoginUserDTO) session.getAttribute("LoginUserInfo");
        List<CombineDTO> userReservationList = reservationService.getUserReservations(user.getEmpId());
        model.addAttribute("userReservationList", userReservationList);

        return "booking/myBookingList"; // 수정된 부분
    }


    // 예약 삭제 메서드
    @PostMapping("/deleteReservation")
    @ResponseBody
    public Map<String,Object> deleteReservationById(@RequestBody Map<String,Object> reservation) {
        Map<String,Object> resultMap = new HashMap<>();

        int result = reservationService.deleteReservationById(String.valueOf(reservation.get("reserveid")));

        resultMap.put("result",result);

        return resultMap;
    }
}
