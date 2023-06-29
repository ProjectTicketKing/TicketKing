package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SeatController {


    private final SeatService seatService;

    @GetMapping("/seats/{type}")
    public String getSeatList(Model model, @PathVariable("type") String type) {
        List<Seat> seatList = seatService.getSeatsByType(type);

        // 서비스 단에서 seatType이 뭔 지 확인해서 해당하는 row와 column 받아오는 코드 작성
        int rows = seatService.getRow(type);
        int columns = seatService.getColumn(type);

        List<Integer> rowList = IntStream.rangeClosed(1, rows).boxed().collect(Collectors.toList());
        List<Integer> columnList = IntStream.rangeClosed(1, columns).boxed().collect(Collectors.toList());

        // 서비스 단에서 Seat의 status가 valid인 좌석리스트
        List<Seat> validSeats = seatList.stream()
                .filter(seat -> "valid".equals(seat.getStatus()))
                .collect(Collectors.toList());



        model.addAttribute("rows", rowList);
        model.addAttribute("columns", columnList);

        return "usr/concert/remain_seat";
    }

}
