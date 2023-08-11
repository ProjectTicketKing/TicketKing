package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.Practice.entity.Practice;
import com.example.ticketKing.domain.Practice.repository.PracticeRepository;
import com.example.ticketKing.domain.Practice.service.PracticeService;
import com.example.ticketKing.domain.Seat.dto.SktRsData;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.global.rq.Rq;
import com.example.ticketKing.global.schedule.SeatStatusScheduler;
import com.example.ticketKing.global.security.SecurityMember;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private final PracticeService practiceService;
    private final Rq rq;
    private final SeatStatusScheduler seatStatusScheduler;
    private final MemberService memberService;
    private final PracticeRepository practiceRepository;


    private void startSeatStatusUpdateSchedule(String hall, String type, String level) {
        seatStatusScheduler.startSeatStatusUpdateSchedule(seatService, hall, type, level);
    }

    public void stopSeatStatusUpdateSchedule() {
        if (seatStatusScheduler != null) {
            seatStatusScheduler.stopSeatStatusUpdateSchedule();
        }
    }



    @GetMapping("/usr/concert/{hall}/{level}/cost")
    public String showConcertCost(Model model, @PathVariable String hall, @PathVariable String level, 
                                  @AuthenticationPrincipal SecurityMember securityMember) {
        Long memberId = securityMember.getId();
        Member member = memberService.getMemberFromUsername(securityMember.getUsername());
        // 가장 최신의 선택 기록을 가져옵니다.
        Optional<Practice> latestPractice = practiceRepository.findTopByMemberIdOrderBySeatSelectionTimeDesc(memberId);


        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        model.addAttribute("latestPractice", latestPractice.orElse(null));
        stopScheduledExecutorService();
        return "usr/concert/concert_cost"; }


    @GetMapping("/usr/concert/{hall}/{level}/seats/{type}")
    public String getSeatList(Model model,
                              @PathVariable("hall") String hall,
                              @PathVariable("type") String type,
                              @PathVariable("level") String level) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("type", type);
        model.addAttribute("selectedLevel",level);

        startSeatStatusUpdateSchedule(hall, type, level);


        return "usr/concert/remain_seat";
    }

    // 애플리케이션 컨텍스트 종료 시 스케줄링 종료 메서드 호출
    @PreDestroy
    public void stopScheduledExecutorService() {
        stopSeatStatusUpdateSchedule();
    }

    @MessageMapping("/seats/{hall}/{type}/seatInfo")
    @SendTo("/topic/seats/{hall}/{type}")
    public SktRsData sendChatMessage(@DestinationVariable String hall, @DestinationVariable String type, SeatRequest request
    ) {
        // 가져온 Seat의 status가 valid이면 => valid
        // 가져온 Seat의 status가 invalid이면 => invalid
        String status = seatService.checkSeatStatus(hall, type, request.getRow(), request.getColumn());
        Integer row = request.getRow();
        Integer column = request.getColumn();
        List<Integer> rowCol = new ArrayList<>();
        rowCol.add(row);
        rowCol.add(column);
        SktRsData seatData = new SktRsData(status,rowCol);

        return seatData;

    }


    @MessageMapping("/seats/{hall}/{type}/confirmInfo")
    @SendTo("/topic/seats/{hall}/{type}")
    public SktRsData sendConfirmMessage(@DestinationVariable String hall, @DestinationVariable String type, SeatRequest request,
                                        @AuthenticationPrincipal SecurityMember securityMember) {

        // 가져온 Seat의 status가 valid이면 => valid
        // 가져온 Seat의 status가 invalid이면 => invalid
        String status = seatService.checkSeatStatus(hall, type, request.getRow(), request.getColumn());
        Integer row = request.getRow();
        Integer column = request.getColumn();
        List<Integer> rowCol = new ArrayList<>();
        rowCol.add(row);
        rowCol.add(column);
        SktRsData seatData = new SktRsData(status,rowCol);

        if(status.equals("valid")) {
            practiceService.register(hall,type, request.getRow(), request.getColumn(), securityMember.getId());
            seatService.seatStatusSave(hall,type, request.getRow(), request.getColumn());
        }

        return seatData;


    }

}