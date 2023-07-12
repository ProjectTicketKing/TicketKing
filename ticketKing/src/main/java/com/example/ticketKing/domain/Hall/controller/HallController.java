package com.example.ticketKing.domain.Hall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HallController {
    @GetMapping("/usr/concert/{hall}/{level}/stage")
    public String showConcertSeatArea(Model model, @PathVariable("hall")String hall,@PathVariable String level) {
        model.addAttribute("hallValue",hall);
        model.addAttribute("selectedLevel",level);
        return "usr/hall/"+hall;
    }

}