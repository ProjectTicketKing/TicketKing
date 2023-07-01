package com.example.ticketKing.domain.Seat.controller;


import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.service.SeatService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        // 서비스 단에서 seatType이 뭔 지 확인해서 해당하는 row와 column 받아오는 코드 작성
        int rows = seatService.getRow(hall, type);
        int columns = seatService.getColumn(hall, type);

        // 유효한 좌석들
        int[][] validSeats;
        validSeats = seatService.getValidSeats(hall, type);

        // 2차원 배열을 리스트로 변환
        List<List<Integer>> validSeatsList = Arrays.stream(validSeats)
                .map(row -> Arrays.stream(row).boxed().collect(Collectors.toList()))
                .collect(Collectors.toList());

        model.addAttribute("rows", rows);
        model.addAttribute("columns", columns);
        model.addAttribute("validSeats", validSeatsList);

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

}