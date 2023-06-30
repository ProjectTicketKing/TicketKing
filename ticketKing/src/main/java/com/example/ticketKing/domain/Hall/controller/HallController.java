package com.example.ticketKing.domain.Hall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HallController {
    @GetMapping("/usr/hall/KSPOHall")
    public String showConcertSeatArea() {
        return "usr/hall/hall_KSPOHall";
    }
    @GetMapping("/usr/hall/OLYSDMHall")
    public String showConcertSeatArea2() {
        return "usr/hall/hall_OLYSDMHall";
    }

}
