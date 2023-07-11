package com.example.ticketKing.domain.Concert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ConcertController {
    @GetMapping("/usr/concert/{hall}")
    public String showConcert(Model model,@PathVariable String hall) {
        model.addAttribute("hallValue", hall);
        return "usr/concert/concert";
    }


    @GetMapping("/usr/concert/{hall}/cost")
    public String showConcertCost(Model model, @PathVariable String hall) {return "usr/concert/concert_cost"; }

    @GetMapping("/usr/concert/{hall}/date")
    public String showConcertDate(Model model, @PathVariable String hall) {
        model.addAttribute("hallValue", hall);
        return "usr/concert/concert_date";
    }


    @GetMapping("/usr/concert/{hall}/delivery")
    public String showConcertDelivery(Model model, @PathVariable String hall) {
        return "usr/concert/concert_delivery";
    }


    @GetMapping("/usr/concert/{hall}/payment")
    public String showConcertPayment(Model model, @PathVariable String hall) {
        return "usr/concert/concert_payment";
    }
    @GetMapping("/usr/concert/{hall}/fee")
    public String showConcertFee(Model model, @PathVariable String hall) {
        return "usr/concert/concert_fee";
    }



}

