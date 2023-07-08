package com.example.ticketKing.domain.Hall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HallController {
    @GetMapping("/usr/concert/{hall}")
    public String showConcertSeatArea(@PathVariable("hall")String hallName) {
        return "usr/hall/"+hallName;
    }


}
