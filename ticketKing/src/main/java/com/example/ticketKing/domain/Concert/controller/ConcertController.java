package com.example.ticketKing.domain.Concert.controller;

import com.example.ticketKing.domain.Seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ConcertController {

    private final SeatService seatService;

    @PreAuthorize("isAuthenticated()") // 로그인 해야만 접속가능
    @GetMapping("/usr/concert/{hall}")
    public String showConcert(Model model,@PathVariable String hall) {
        model.addAttribute("hallValue", hall);

        return "usr/concert/concert";
    }


    @GetMapping("/usr/concert/{hall}/{level}/date")
    public String showConcertDate(Model model, @PathVariable String hall, @PathVariable String level) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        return "usr/concert/concert_date";
    }


    @GetMapping("/usr/concert/{hall}/{level}/delivery")
    public String showConcertDelivery(Model model, @PathVariable String hall,@PathVariable String level) {

        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        return "usr/concert/concert_delivery";

    }


    @GetMapping("/usr/concert/{hall}/{level}/payment")
    public String showConcertPayment(Model model, @PathVariable String hall,@PathVariable String level) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        return "usr/concert/concert_payment";
    }
    @GetMapping("/usr/concert/{hall}/{level}/fee")
    public String showConcertFee(Model model, @PathVariable String hall ,@PathVariable String level) {

        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        return "usr/concert/concert_fee";
    }
    



}

