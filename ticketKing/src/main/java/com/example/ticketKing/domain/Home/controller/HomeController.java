package com.example.ticketKing.domain.Home.controller;

import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.global.schedule.SeatStatusScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class HomeController {

    private final SeatStatusScheduler seatStatusScheduler;
    private final SeatService seatService;
    @GetMapping("/")
    public String showMain()
    {
        seatStatusScheduler.stopSeatStatusUpdateSchedule();
        seatService.initializeAllSeats();
        return "usr/main/home";
    }

}