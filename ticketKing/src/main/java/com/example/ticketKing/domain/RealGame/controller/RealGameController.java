package com.example.ticketKing.domain.RealGame.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class RealGameController {

    @GetMapping("/usr/{env}/concert/{hall}")
    public String showRealGamePage(
            @PathVariable("hall") String hall,
            @PathVariable("env") String realGame,
            Model model) {

        model.addAttribute("environment", realGame);
        model.addAttribute("hallValue", hall);

        return "usr/concert/concertVer2";
    }
}
