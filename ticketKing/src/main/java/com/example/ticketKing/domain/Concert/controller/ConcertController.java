package com.example.ticketKing.domain.Concert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ConcertController {
    @GetMapping("/usr/concert/concert")
    public String showConcert() {
        return "usr/concert/concert";
    }


    @GetMapping("/usr/concert/cost")
    public String showConcertCost() {return "usr/concert/concert_cost"; }

    @GetMapping("/usr/concert/date")
    public String showConcertDate() {
        return "usr/concert/concert_date";
    }



}

