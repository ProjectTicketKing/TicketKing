package com.example.ticketKing.domain.Seat.controller;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.global.security.SecurityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ConcertController {

    private final SeatService seatService;
    private final MemberService memberService;



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}")
    public String showRealGamePage(
            @PathVariable("hall") String hall,
            @PathVariable("env") String env,
            Model model) {

        model.addAttribute("env", env);
        model.addAttribute("hallValue", hall);

        if(env.equals("realGame")){
            return "usr/concert/concert_ver1";
        } else if (env.equals("virtualGame")) {
            return "usr/concert/concert_ver2";
        }
        return "usr/concert/concert_ver2";
    }


    // 실제 경쟁 환경 (env1)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/date")
    public String showConcertDate(Model model, @PathVariable String hall,@PathVariable String env) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("env",env);
        return "usr/concert/concert_date_ver1";

    }

    // 가상 경제 환경 (env2)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/{level}/date")
    public String showConcertDateSecondEnv(Model model, @PathVariable String hall, @PathVariable String level,@PathVariable String env) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("env",env);
        return "usr/concert/concert_date_ver2";
    }


    // 실제 경쟁 환경 (env1)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/stage")
    public String showConcertSeatArea(Model model, @PathVariable("hall")String hall,@PathVariable String env) {
        model.addAttribute("hallValue",hall);
        model.addAttribute("env",env);
        return "usr/hallEnv1/"+hall;
    }

    //  가상 경제 환경 (env2)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/{level}/stage")
    public String showConcertSeatAreaSecondEnv(Model model, @PathVariable("hall")String hall,@PathVariable String level,@PathVariable String env) {
        model.addAttribute("hallValue",hall);
        model.addAttribute("selectedLevel",level);
        model.addAttribute("env",env);
        return "usr/hallEnv2/"+hall;
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/concert/{hall}/delivery")
    public String showConcertDelivery(Model model, @PathVariable String hall,
                                       @AuthenticationPrincipal SecurityMember securityMember) {

        model.addAttribute("hallValue", hall);

        return "usr/concert/concert_delivery";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/concert/{hall}/payment")
    public String showConcertPayment(Model model, @PathVariable String hall,
                                     @AuthenticationPrincipal SecurityMember securityMember) {

        model.addAttribute("hallValue", hall);

        return "usr/concert/concert_payment";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/concert/{hall}/fee")
    public String showConcertFee(Model model, @PathVariable String hall,
                                 @AuthenticationPrincipal SecurityMember securityMember) {


        model.addAttribute("hallValue", hall);

        return "usr/concert/concert_fee";
    }






}
