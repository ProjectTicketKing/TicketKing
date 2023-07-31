package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Practice.service.PracticeService;
import com.example.ticketKing.domain.Seat.dto.SktRsData;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.global.rq.Rq;
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

    private ScheduledExecutorService executor;
    private Set<String> scheduledHalls = new HashSet<>();

    Integer finalRow = 0;
    Integer finalCol = 0;


    // 스케줄링 시작 메서드
    private void startSeatStatusUpdateSchedule(String hall, String type, String level) {
        executor = Executors.newSingleThreadScheduledExecutor();


//
        long initialDelay = 0;
        long period = 0;
        System.out.println("level!!!!!!!!");
        System.out.println(level);
        if (level.equals("basic")) {
            initialDelay = 5000;
            period = 5000; // 5 seconds
        } else if (level.equals("advanced")) {
            initialDelay = 1000;
            period = 1000; // 3 seconds
        }
        executor.scheduleAtFixedRate(() -> seatService.updateRandomSeatStatusToInvalid(hall, type), initialDelay, period, TimeUnit.MILLISECONDS);
    }



    // 스케줄링 중지 메서드
    public void stopSeatStatusUpdateSchedule() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    @GetMapping("/")
    public String showMain() {

        stopScheduledExecutorService();
        return "usr/main/home";
    }

    @GetMapping("/usr/concert/{hall}/{level}/cost")
    public String showConcertCost(Model model, @PathVariable String hall, @PathVariable String level) {
        model.addAttribute("hallValue", hall);
        model.addAttribute("selectedLevel", level);
        stopScheduledExecutorService();
        return "usr/concert/concert_cost"; }


    @GetMapping("/usr/concert/{hall}/{level}/seats/{type}")
    public String getSeatList(Model model,
                              @PathVariable("hall") String hall,
                              @PathVariable("type") String type,
                              @PathVariable("level") String level) {
        model.addAttribute("hall", hall);
        model.addAttribute("type", type);
        model.addAttribute("selectedLevel",level);

        startSeatStatusUpdateSchedule(hall, type, level);

        // 스케줄링이 시작되지 않았을 때만 스케줄링 시작
//        if (!scheduledHalls.contains(hall)) {
//            startSeatStatusUpdateSchedule(hall, type, level);
//            scheduledHalls.add(hall);
//        }


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

        // RsData 만들어서 status, message, data 만들어서
        // Signal Type
        // subscribe단에서 type을 줘서 프론트에서 if문을 써서 처리할 수 있도록
        // data generic타입으로
        // 프론트 단에서 리스트처리

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