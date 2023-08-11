package com.example.ticketKing.domain.Concert.controller;

import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.Practice.entity.Practice;
import com.example.ticketKing.domain.Practice.repository.PracticeRepository;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.global.security.SecurityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ConcertController {

    private final SeatService seatService;
    private final MemberService memberService;
    private final PracticeRepository practiceRepository;

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
    public String showConcertDelivery(Model model, @PathVariable String hall,@PathVariable String level,
                                      @AuthenticationPrincipal SecurityMember securityMember) {

        Long memberId = securityMember.getId();
        Member member = memberService.getMemberFromUsername(securityMember.getUsername());
        // 가장 최신의 선택 기록을 가져옵니다.
        Optional<Practice> latestPractice = practiceRepository.findTopByMemberIdOrderBySeatSelectionTimeDesc(memberId);
        
        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("latestPractice", latestPractice.orElse(null));
        
        return "usr/concert/concert_delivery";

    }


    @GetMapping("/usr/concert/{hall}/{level}/payment")
    public String showConcertPayment(Model model, @PathVariable String hall,@PathVariable String level,
                                     @AuthenticationPrincipal SecurityMember securityMember) {
        Long memberId = securityMember.getId();
        Member member = memberService.getMemberFromUsername(securityMember.getUsername());
        // 가장 최신의 선택 기록을 가져옵니다.
        Optional<Practice> latestPractice = practiceRepository.findTopByMemberIdOrderBySeatSelectionTimeDesc(memberId);

        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("latestPractice", latestPractice.orElse(null));

        return "usr/concert/concert_payment";
    }
    @GetMapping("/usr/concert/{hall}/{level}/fee")
    public String showConcertFee(Model model, @PathVariable String hall ,@PathVariable String level,
                                 @AuthenticationPrincipal SecurityMember securityMember) {

        Long memberId = securityMember.getId();
        Member member = memberService.getMemberFromUsername(securityMember.getUsername());
        // 가장 최신의 선택 기록을 가져옵니다.
        Optional<Practice> latestPractice = practiceRepository.findTopByMemberIdOrderBySeatSelectionTimeDesc(memberId);

        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("latestPractice", latestPractice.orElse(null));

        return "usr/concert/concert_fee";
    }




}

