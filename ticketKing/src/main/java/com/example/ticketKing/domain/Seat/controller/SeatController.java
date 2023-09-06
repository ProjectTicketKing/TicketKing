package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.PracticeResult.entity.PracticeResult;
import com.example.ticketKing.domain.PracticeResult.repository.PracticeResultRepository;
import com.example.ticketKing.domain.PracticeResult.service.PracticeResultService;
import com.example.ticketKing.domain.Seat.dto.SktRsData;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.domain.VirtualSeat.service.VirtualSeatService;
import com.example.ticketKing.global.rq.Rq;
import com.example.ticketKing.global.schedule.SeatStatusScheduler;
import com.example.ticketKing.global.security.SecurityMember;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private final PracticeResultService practiceService;
    private final Rq rq;
    private final SeatStatusScheduler seatStatusScheduler;
    private final MemberService memberService;
    private final VirtualSeatService virtualSeatService;
    private final PracticeResultRepository practiceRepository;


    private void startSeatStatusUpdateSchedule(String hall, String type) {
        seatStatusScheduler.startSeatStatusUpdateSchedule(seatService, hall, type);
    }

    private void startVirtualSeatStatusUpdateSchedule(String hall, String type, String level) {
        seatStatusScheduler.startVirtualSeatStatusUpdateSchedule(seatService,hall,type,level);
    }


    public void stopSeatStatusUpdateSchedule() {
        if (seatStatusScheduler != null) {
            seatStatusScheduler.stopSeatStatusUpdateSchedule();
        }
    }

    public void stopVirtualSeatStatusUpdateSchedule() {
        if (seatStatusScheduler != null) {
            seatStatusScheduler.stopVirtualSeatStatusUpdateSchedule();
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/seats/{type}")
    public String getSeatList(Model model,
                              @PathVariable("hall") String hall,
                              @PathVariable("type") String type,
                              @PathVariable("env") String env
                              ) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("type", type);
        model.addAttribute("env",env);

        startSeatStatusUpdateSchedule(hall, type);
        return "usr/concert/remain_seat_ver1";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/{level}/seats/{type}")
    public String getVirtualSeatList(Model model,
                              @PathVariable("hall") String hall,
                              @PathVariable("type") String type,
                              @PathVariable("level") String level,
                                     @PathVariable("env") String env) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("type", type);
        model.addAttribute("selectedLevel",level);
        model.addAttribute("env",env);

        startVirtualSeatStatusUpdateSchedule(hall,type,level);
        return "usr/concert/remain_seat_ver2";
    }




    // 애플리케이션 컨텍스트 종료 시 스케줄링 종료 메서드 호출
    @PreDestroy
    public void stopScheduledExecutorService() {
        stopSeatStatusUpdateSchedule();
    }

    @MessageMapping("/seats/{env}/{hall}/{type}/seatInfo")
    @SendTo("/topic/seats/{env}/{hall}/{type}")
    public SktRsData sendSeatStatus(@DestinationVariable String hall,
                                     @DestinationVariable String env,
                                     @DestinationVariable String type,
                                     SeatRequest request
    ) {
        if(env.equals("realGame")){

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
        else if(env.equals("virtualGame")){
            // 가져온 Seat의 status가 valid이면 => valid
            // 가져온 Seat의 status가 invalid이면 => invalid
            String status = virtualSeatService.checkSeatStatus(hall, type, request.getRow(), request.getColumn());
            Integer row = request.getRow();
            Integer column = request.getColumn();
            List<Integer> rowCol = new ArrayList<>();
            rowCol.add(row);
            rowCol.add(column);
            SktRsData seatData = new SktRsData(status,rowCol);

            return seatData;
        }

    return null;

    }


    @MessageMapping("/seats/{env}/{hall}/{type}/confirmInfo")
    @SendTo("/topic/seats/{env}/{hall}/{type}")
    public SktRsData sendSeatConfirm(@DestinationVariable String hall,
                                        @DestinationVariable String type,
                                        @DestinationVariable String env,
                                        SeatRequest request,
                                        @AuthenticationPrincipal SecurityMember securityMember) {


        if(env.equals("realGame")){
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
        else if(env.equals("virtualGame")){
            // 가져온 Seat의 status가 valid이면 => valid
            // 가져온 Seat의 status가 invalid이면 => invalid
            String status = virtualSeatService.checkSeatStatus(hall, type, request.getRow(), request.getColumn());
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


        return null;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usr/{env}/concert/{hall}/cost")
    public String showConcertCost(Model model, @PathVariable String hall, @PathVariable String env,
                                  @AuthenticationPrincipal SecurityMember securityMember) {
        Long memberId = securityMember.getId();
        Member member = memberService.getMemberFromUsername(securityMember.getUsername());
        // 가장 최신의 선택 기록을 가져옵니다.
        Optional<PracticeResult> latestPractice = practiceRepository.findTopByMemberIdOrderBySeatSelectionTimeDesc(memberId);


        model.addAttribute("hallValue", hall);
        model.addAttribute("env",env);
        model.addAttribute("latestPractice", latestPractice.orElse(null));

        if(env.equals("realGame")){
            stopScheduledExecutorService();
        }
        else if(env.equals("virtualGame")){
            stopVirtualSeatStatusUpdateSchedule();
        }

        return "usr/concert/concert_cost"; }


}