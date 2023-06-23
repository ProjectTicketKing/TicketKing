package com.example.ticketKing.domain.Concert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ConcertController {
    @PostMapping("/usr/concert/concert")
    public String showConcert() {
        return "usr/concert/concert";
    }
}


