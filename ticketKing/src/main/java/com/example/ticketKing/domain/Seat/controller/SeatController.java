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

    @GetMapping("/usr/concert/{hall}/seats/{type}")
    public String getSeatList(Model model,@PathVariable("hall") String hall ,@PathVariable("type") String type) {

//        프론트 단으로 넘겨줄 수 있으면 이 데이터를 넘겨주기
        List<Seat> seatList = seatService.getSeatsByHallAndType(hall,type);

        // 서비스 단에서 seatType이 뭔 지 확인해서 해당하는 row와 column 받아오는 코드 작성
        int rows = seatService.getRow(hall,type);
        int columns = seatService.getColumn(hall,type);


        model.addAttribute("rows", rows);
        model.addAttribute("columns", columns);

        return "usr/concert/remain_seat";
    }

}
