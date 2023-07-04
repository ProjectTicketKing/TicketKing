package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.service.SeatService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@RequiredArgsConstructor
@Controller
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private ScheduledExecutorService executor;
    private boolean isSchedulingStarted = false;

    // 스케줄링 시작 메서드
    private void startSeatStatusUpdateSchedule(String hall, String type) {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> seatService.updateRandomSeatStatusToInvalid(hall, type), 5000, 5000, TimeUnit.MILLISECONDS);
    }

    // 스케줄링 중지 메서드
    private void stopSeatStatusUpdateSchedule() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    @GetMapping("/usr/concert/{hall}/seats/{type}")
    public String getSeatList(Model model, @PathVariable("hall") String hall, @PathVariable("type") String type) {
        model.addAttribute("hall", hall);
        model.addAttribute("type", type);

        // 스케줄링이 시작되지 않았을 때만 스케줄링 시작
        if (!isSchedulingStarted) {
            startSeatStatusUpdateSchedule(hall, type);
            isSchedulingStarted = true;
        }

        return "usr/concert/remain_seat";
    }

    // 애플리케이션 컨텍스트 종료 시 스케줄링 종료 메서드 호출
    @PreDestroy
    private void stopScheduledExecutorService() {
        stopSeatStatusUpdateSchedule();
    }

    @MessageMapping("/seats/{hall}/{type}/seatInfo")
    @SendTo("/topic/seats/{hall}/{type}")
    public String sendChatMessage(@DestinationVariable String hall, @DestinationVariable String type, SeatRequest request) {


        log.info("hall : {}", hall);
        log.info("type : {}", type);
        log.info("row : {}", request.getRow());
        log.info("column : {}", request.getColumn());

        return "false";
    }

}