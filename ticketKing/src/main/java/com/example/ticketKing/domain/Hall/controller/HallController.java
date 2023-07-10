package com.example.ticketKing.domain.Hall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HallController {
    @GetMapping("/usr/concert/{hall}/stage")
    public String showConcertSeatArea(Model model, @PathVariable("hall")String hallName) {
        model.addAttribute("hallName",hallName);
        return "usr/hall/"+hallName;
    }

}